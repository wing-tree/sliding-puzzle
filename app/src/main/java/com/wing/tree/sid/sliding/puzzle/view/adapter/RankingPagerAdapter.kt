package com.wing.tree.sid.sliding.puzzle.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wing.tree.sid.core.constant.THREE
import com.wing.tree.sid.sliding.puzzle.view.RankingListFragment

class RankingPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return RankingListFragment.instance(position)
    }

    companion object {
        const val ITEM_COUNT = THREE
    }
}
