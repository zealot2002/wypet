package com.qh.wypet.ui.social

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SocialPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecommendedFragment.newInstance()
            1 -> FollowingFragment.newInstance()
            2 -> FamilyFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
} 