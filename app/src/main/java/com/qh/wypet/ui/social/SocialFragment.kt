package com.qh.wypet.ui.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.qh.wypet.R
import com.qh.wypet.databinding.FragmentSocialBinding
import com.qh.wypet.ui.base.BaseFragment

class SocialFragment : BaseFragment() {

    private var _binding: FragmentSocialBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var pagerAdapter: SocialPagerAdapter
    private val tabTitles = arrayOf(R.string.tab_recommended, R.string.tab_following, R.string.tab_family)
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupViewPager()
        setupTabLayout()
    }
    
    private fun setupViewPager() {
        pagerAdapter = SocialPagerAdapter(this)
        binding.viewPager.apply {
            adapter = pagerAdapter
            // Prevent swiping too quickly
            offscreenPageLimit = 2
            // Reduce sensitivity to avoid accidental page changes
            isUserInputEnabled = true
        }
    }
    
    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(tabTitles[position])
        }.attach()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SocialFragment()
    }
} 