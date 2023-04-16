package com.wing.tree.sid.sliding.puzzle.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.wing.tree.sid.core.constant.*
import com.wing.tree.sid.core.extension.long
import com.wing.tree.sid.domain.entity.Nickname
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.ad.InterstitialAdLoader
import com.wing.tree.sid.sliding.puzzle.databinding.FragmentResultBinding
import com.wing.tree.sid.sliding.puzzle.databinding.NotRankedBinding
import com.wing.tree.sid.sliding.puzzle.databinding.RankedBinding
import com.wing.tree.sid.sliding.puzzle.extension.*
import com.wing.tree.sid.sliding.puzzle.viewModel.ResultViewModel
import com.wing.tree.sid.sliding.puzzle.viewState.ResultViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>() {
    private val interstitialAdLoader by lazy {
        InterstitialAdLoader()
    }

    private val viewModel by viewModels<ResultViewModel>()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            materialToolbar.setNavigationOnClickListener {
                finish()
            }

            lottieAnimationView.addAnimatorListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        lottieAnimationView.fadeOut()
                    }
                }
            )

            home.setOnClickListener {
                finish()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    with(binding.loading) {
                        if (viewState is ResultViewState.Loading) {
                            show()
                        } else {
                            hide()
                        }
                    }

                    when (viewState) {
                        ResultViewState.Loading -> Unit
                        is ResultViewState.Content -> with(binding) {
                            when (viewState) {
                                is ResultViewState.Content.Ranked -> with(ranked) {
                                    bind(viewState)
                                    fadeIn(startDelay = ONE_HUNDRED.long)
                                }

                                is ResultViewState.Content.NotRanked -> with(notRanked) {
                                    bind(viewState)
                                    fadeIn(startDelay = ONE_HUNDRED.long)
                                }
                            }
                        }

                        is ResultViewState.Error -> {
                            Toast.makeText(requireActivity(), viewState.cause.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            if (viewModel.adFreeChecker().not()) {
                interstitialAdLoader.load(requireContext())
            }
        }
    }

    private fun finish() {
        lifecycleScope.launch {
            if (viewModel.adFreeChecker()) {
                popBackStack()
            } else {
                interstitialAdLoader.show(
                    activity = requireActivity(),
                    fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdShowedFullScreenContent() {
                            popBackStack()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            popBackStack()
                        }
                    }
                )
            }
        }
    }

    private fun Long.format(): String {
        val hours = TimeUnit.MILLISECONDS.toHours(this)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(this).minus(TimeUnit.HOURS.toMinutes(hours))
        val seconds = TimeUnit.MILLISECONDS.toSeconds(this).minus(TimeUnit.MINUTES.toSeconds(minutes))
        val milliseconds = rem(ONE_THOUSAND).div(TEN)

        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d.%02d",
            hours,
            minutes,
            seconds,
            milliseconds
        )
    }

    private fun NotRankedBinding.bind(viewState: ResultViewState.Content.NotRanked) {
        val rankingParameter = viewState.rankingParameter

        solved.setDongleText(getString(R.string.solved, rankingParameter.size))
        playTime.setDongleText(rankingParameter.playTime.format())
    }

    private fun RankedBinding.bind(viewState: ResultViewState.Content.Ranked) {
        val rankingParameter = viewState.rankingParameter

        nickname.editText?.setDonglePadding()

        registerForRanking.setOnClickListener {
            val editText = nickname.editText
            val nickname = editText?.text ?: EMPTY

            lifecycleScope.launch {
                if (nickname.isBlank()) {
                    editText?.error = string(R.string.please_enter_a_nickname)
                } else {
                    viewModel.registerForRanking(Nickname(nickname)).onSuccess {
                        Toast.makeText(requireActivity(), rank.text, Toast.LENGTH_SHORT).show()
                    }.onFailure {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    finish()
                }
            }
        }

        rank.setDongleText(getString(R.string.ranked, "${viewState.rank}"))
        solved.setDongleText(getString(R.string.solved, rankingParameter.size))
        playTime.setDongleText(rankingParameter.playTime.format())
    }
}
