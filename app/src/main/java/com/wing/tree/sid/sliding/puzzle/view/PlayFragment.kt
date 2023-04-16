package com.wing.tree.sid.sliding.puzzle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.facebook.shimmer.Shimmer.AlphaHighlightBuilder
import com.wing.tree.sid.core.constant.ONE_HUNDRED
import com.wing.tree.sid.core.constant.ONE_THOUSAND
import com.wing.tree.sid.core.extension.long
import com.wing.tree.sid.core.extension.milliseconds
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.databinding.FragmentPlayBinding
import com.wing.tree.sid.sliding.puzzle.extension.*
import com.wing.tree.sid.sliding.puzzle.view.adapter.TileListAdapter
import com.wing.tree.sid.sliding.puzzle.viewModel.PlayViewModel
import com.wing.tree.sid.sliding.puzzle.viewState.PlayViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class PlayFragment : BaseFragment<FragmentPlayBinding>() {
    private val navArgs by navArgs<PlayFragmentArgs>()
    private val tileListAdapter by lazy {
        TileListAdapter(navArgs.size) {
            viewModel.updateSolved(it.isOrdered)
        }
    }

    private val viewModel by viewModels<PlayViewModel>()

    private var simpleItemAnimator: SimpleItemAnimator? = null

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentPlayBinding {
        return FragmentPlayBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bind()
        viewModel.collect()
    }

    override fun onPause() {
        viewModel.savePuzzle(tileListAdapter.sequence)

        super.onPause()
    }

    private fun onPlaying(viewState: PlayViewState.Content.Playing) {
        val puzzle = viewState.puzzle
        val text = puzzle.playTime.format()

        tileListAdapter.submitList(puzzle.tiles)

        with(binding) {
            playTime.setDongleText(text)

            if (tiles.isNotVisible) {
                tiles.fadeIn(startDelay = ONE_HUNDRED.long)
            }
        }

        simpleItemAnimator?.supportsChangeAnimations = false
        viewModel.stopwatch.start()
    }

    private fun onSolved() {
        val shimmer = AlphaHighlightBuilder()
            .setBaseAlpha(float(R.dimen.shimmer_base_alpha))
            .setHighlightAlpha(float(R.dimen.shimmer_highlight_alpha))
            .build()

        binding.shimmerFrameLayout.setShimmer(shimmer)
        binding.shimmerFrameLayout.showShimmer(true)
        binding.shimmerFrameLayout.startShimmer()
        tileListAdapter.onSolved()

        lifecycleScope.launch {
            delay(ONE_THOUSAND.milliseconds)

            with(viewModel) {
                stopwatch.stop()

                val directions = PlayFragmentDirections
                    .actionPlayFragmentToResultFragment(rankingParameter)

                navigate(directions)
            }
        }
    }

    private fun FragmentPlayBinding.bind() {
        materialToolbar.setNavigationOnClickListener {
            popBackStack()
        }

        tiles.apply {
            val size = navArgs.size
            val itemCount = size.int.inc()
            val spanCount = size.column

            adapter = tileListAdapter
            layoutManager = object : GridLayoutManager(context, spanCount) {
                override fun canScrollHorizontally(): Boolean = false
                override fun canScrollVertically(): Boolean = false
            }.apply {
                initialPrefetchItemCount = itemCount
            }

            itemAnimator?.let {
                if (it is SimpleItemAnimator) {
                    simpleItemAnimator = it
                }
            }
        }

        reset.setOnClickListener {
            simpleItemAnimator?.supportsChangeAnimations = true
            viewModel.reset()
        }
    }

    private fun Long.format(): String {
        val hours = TimeUnit.MILLISECONDS.toHours(this)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(this).minus(TimeUnit.HOURS.toMinutes(hours))
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(this).minus(TimeUnit.MINUTES.toSeconds(minutes))

        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )
    }

    private fun PlayViewModel.collect() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val periodMills = ONE_THOUSAND

                @OptIn(FlowPreview::class)
                playTime.sample(periodMills.milliseconds).collect {
                    binding.playTime.text = it.format()
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewState.collect { viewState ->
                    when (viewState) {
                        PlayViewState.Loading -> Unit
                        is PlayViewState.Content -> {
                            when (viewState) {
                                is PlayViewState.Content.Playing -> onPlaying(viewState)
                                is PlayViewState.Content.Solved -> onSolved()
                            }
                        }

                        is PlayViewState.Error -> Unit
                    }
                }
            }
        }
    }
}
