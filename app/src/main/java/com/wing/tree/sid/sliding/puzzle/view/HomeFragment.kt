package com.wing.tree.sid.sliding.puzzle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.databinding.FragmentHomeBinding
import com.wing.tree.sid.sliding.puzzle.extension.dimensionPixelSize
import com.wing.tree.sid.sliding.puzzle.view.adapter.ItemDecoration
import com.wing.tree.sid.sliding.puzzle.view.adapter.SizeListAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val sizeListAdapter = SizeListAdapter {
        navigate(HomeFragmentDirections.actionHomeFragmentToPlayFragment(it))
    }

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            sizes.apply {
                adapter = sizeListAdapter
                layoutManager = LinearLayoutManager(context)

                addItemDecoration(
                    ItemDecoration(
                        left = dimensionPixelSize(R.dimen.left_padding),
                        right = dimensionPixelSize(R.dimen.right_padding),
                        top = dimensionPixelSize(R.dimen.padding_between_cards)
                    )
                )
            }

            ranking.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToRankingPagerFragment())
            }
        }

        sizeListAdapter.submitList(Size.values().asList())
    }
}
