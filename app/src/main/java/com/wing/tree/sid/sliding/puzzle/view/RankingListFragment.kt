package com.wing.tree.sid.sliding.puzzle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wing.tree.sid.core.constant.EXTRA
import com.wing.tree.sid.core.constant.TEN
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.databinding.FragmentRankingListBinding
import com.wing.tree.sid.sliding.puzzle.extension.*
import com.wing.tree.sid.sliding.puzzle.view.adapter.ItemDecoration
import com.wing.tree.sid.sliding.puzzle.view.adapter.RankingListAdapter
import com.wing.tree.sid.sliding.puzzle.viewModel.RankingListViewModel
import com.wing.tree.sid.sliding.puzzle.viewState.RankingListViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RankingListFragment : BaseFragment<FragmentRankingListBinding>() {
    private val rankingListAdapter = RankingListAdapter()
    private val viewModel by viewModels<RankingListViewModel>()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRankingListBinding {
        return FragmentRankingListBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.error.retry.setOnClickListener {
            viewModel.retry()
        }

        binding.rankingList.apply {
            adapter = rankingListAdapter
            layoutManager = LinearLayoutManager(context).apply {
                initialPrefetchItemCount = TEN
            }

            setHasFixedSize(true)

            addItemDecoration(
                ItemDecoration(
                    left = dimensionPixelSize(R.dimen.left_padding),
                    right = dimensionPixelSize(R.dimen.right_padding),
                    top = dimensionPixelSize(R.dimen.padding_between_cards)
                )
            )
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.viewState.collect { viewState ->
                    with(binding) {
                        when (viewState) {
                            RankingListViewState.Loading -> {
                                rankingList.fadeOut()
                                error.hide()
                                loading.show()
                            }

                            is RankingListViewState.Content -> {
                                loading.hide()
                                error.hide()
                                rankingListAdapter.submitList(viewState.data) {
                                    if (rankingList.isNotVisible) {
                                        rankingList.fadeIn()
                                    }
                                }
                            }

                            is RankingListViewState.Error -> {
                                binding.rankingList.fadeOut()
                                binding.error.show(viewState.cause)
                                binding.loading.hide()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val PACKAGE_NAME = "com.wing.tree.sid.sliding.puzzle.view"

        const val EXTRA_PAGE = "$PACKAGE_NAME.$EXTRA.page"

        fun instance(page: Int): RankingListFragment {
            val bundle = Bundle().apply {
                putInt(EXTRA_PAGE, page)
            }

            return RankingListFragment().apply {
                arguments = bundle
            }
        }
    }
}
