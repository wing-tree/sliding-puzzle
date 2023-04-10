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
import com.wing.tree.sid.core.constant.ONE_THOUSAND
import com.wing.tree.sid.core.extension.milliseconds
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
            if (it.isOrdered) {
                viewModel.onSolved()
            }
        }
    }

    private val viewModel by viewModels<PlayViewModel>()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentPlayBinding {
        return FragmentPlayBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            tiles.apply {
                val size = navArgs.size
                val itemCount = size.int.inc()
                val spanCount = size.column

                adapter = tileListAdapter
                itemAnimator = null
                layoutManager = object : GridLayoutManager(context, spanCount) {
                    override fun canScrollHorizontally(): Boolean = false
                    override fun canScrollVertically(): Boolean = false
                }.apply {
                    initialPrefetchItemCount = itemCount
                }
            }

            reset.setOnClickListener {
                viewModel.reset()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val periodMills = resources.configLongAnimTime

                @OptIn(FlowPreview::class)
                viewModel.playTime.sample(periodMills.milliseconds).collect {
                    viewBinding.playTime.text = it.format()
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    when (viewState) {
                        PlayViewState.Loading -> {
                            viewBinding.loading.show()
                        }

                        is PlayViewState.Content -> {
                            viewBinding.loading.hide()

                            when (viewState) {
                                is PlayViewState.Content.Playing -> {
                                    val puzzle = viewState.puzzle

                                    viewBinding.playTime.text = puzzle.playTime.format()

                                    tileListAdapter.submitList(puzzle.tiles)
                                    viewModel.stopwatch.start()
                                }

                                is PlayViewState.Content.Solved -> onSolved()
                            }
                        }

                        is PlayViewState.Error -> {
                            viewBinding.loading.hide()
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        viewModel.savePuzzle(tileListAdapter.sequence)

        super.onPause()
    }

    private fun onSolved() {
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

    private fun Long.format(): String {
        val hours = TimeUnit.MILLISECONDS.toHours(this)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(this).minus(TimeUnit.HOURS.toMinutes(hours))
        val seconds = TimeUnit.MILLISECONDS.toSeconds(this).minus(TimeUnit.MINUTES.toSeconds(minutes))

        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )
    }
}
