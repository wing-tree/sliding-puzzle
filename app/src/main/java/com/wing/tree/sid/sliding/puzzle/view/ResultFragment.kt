package com.wing.tree.sid.sliding.puzzle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.wing.tree.sid.core.constant.EMPTY
import com.wing.tree.sid.core.constant.ONE_THOUSAND
import com.wing.tree.sid.core.constant.TEN
import com.wing.tree.sid.domain.entity.Nickname
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.databinding.FragmentResultBinding
import com.wing.tree.sid.sliding.puzzle.extension.*
import com.wing.tree.sid.sliding.puzzle.viewModel.ResultViewModel
import com.wing.tree.sid.sliding.puzzle.viewState.ResultViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>() {
    private val viewModel by viewModels<ResultViewModel>()

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
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

                        findNavController().popBackStack()
                    }
                }
            }

            home.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    when (viewState) {
                        ResultViewState.Loading -> {
                            viewBinding.loading.show()
                        }

                        is ResultViewState.Content -> with(viewBinding) {
                            viewBinding.loading.hide()

                            val rankingParameter = viewState.rankingParameter

                            when (viewState) {
                                is ResultViewState.Content.Ranked -> {
                                    rank.visible()
                                    nickname.visible()
                                    registerForRanking.visible()

                                    solved.setTextAppearance(R.style.DisplaySmall)
                                    playTime.setTextAppearance(R.style.HeadlineLarge)

                                    rank.setDongleText(getString(R.string.ranked, "${viewState.rank}"))
                                }

                                is ResultViewState.Content.NotRanked -> {
                                    rank.gone()
                                    nickname.gone()
                                    registerForRanking.gone()

                                    solved.setTextAppearance(R.style.DisplayMedium)
                                    playTime.setTextAppearance(R.style.DisplaySmall)
                                }
                            }

                            solved.setDongleText(getString(R.string.solved, rankingParameter.size))
                            playTime.setDongleText(rankingParameter.playTime.format())
                        }

                        is ResultViewState.Error -> {
                            viewBinding.loading.hide()
                            Toast.makeText(requireActivity(), viewState.cause.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
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
}
