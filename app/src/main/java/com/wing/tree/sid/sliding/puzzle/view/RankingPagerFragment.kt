package com.wing.tree.sid.sliding.puzzle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.wing.tree.sid.core.constant.ONE
import com.wing.tree.sid.core.constant.TWO
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.sliding.puzzle.databinding.FragmentRankingPagerBinding
import com.wing.tree.sid.sliding.puzzle.extension.popBackStack
import com.wing.tree.sid.sliding.puzzle.view.adapter.RankingPagerAdapter

class RankingPagerFragment : BaseFragment<FragmentRankingPagerBinding>() {
    private val onPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.currentPage.text = "${position.inc()}"
        }
    }

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

        with(binding) {
            materialToolbar.setNavigationOnClickListener {
                popBackStack()
            }

            rankingPager.apply {
                adapter = rankingPagerAdapter
                registerOnPageChangeCallback(onPageChangeCallback)
            }

            previous.setOnClickListener {
                val currentItem = rankingPager.currentItem
                val previousItem = currentItem.dec()

                with(rankingPager) {
                    if (previousItem in ZERO..TWO) {
                        setCurrentItem(previousItem, true)
                    }
                }
            }

            currentPage.text = "$ONE"
            pageCount.text = "${RankingPagerAdapter.ITEM_COUNT}"

            next.setOnClickListener {
                val currentItem = rankingPager.currentItem
                val nextItem = currentItem.inc()

                with(rankingPager) {
                    if (nextItem in ZERO..TWO) {
                        setCurrentItem(nextItem, true)
                    }
                }
            }
        }
    }
}
