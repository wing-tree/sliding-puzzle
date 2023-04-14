package com.wing.tree.sid.sliding.puzzle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wing.tree.sid.sliding.puzzle.databinding.FragmentRankingPagerBinding
import com.wing.tree.sid.sliding.puzzle.view.adapter.RankingPagerAdapter

class RankingPagerFragment : BaseFragment<FragmentRankingPagerBinding>() {
    private val rankingPagerAdapter by lazy {
        RankingPagerAdapter(this)
    }

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRankingPagerBinding {
        return FragmentRankingPagerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            rankingPager.apply {
                adapter = rankingPagerAdapter
            }
        }
    }
}
