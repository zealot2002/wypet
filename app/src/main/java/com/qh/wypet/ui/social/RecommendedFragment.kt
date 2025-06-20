package com.qh.wypet.ui.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.qh.wypet.databinding.FragmentRecommendedBinding
import com.qh.wypet.ui.base.BaseFragment

class RecommendedFragment : BaseFragment(), SocialFeedAdapter.SocialFeedInteractionListener {

    private var _binding: FragmentRecommendedBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var adapter: SocialFeedAdapter
    private val feedItems = SampleDataProvider.getSampleRecommendedData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
    }
    
    /**
     * 覆盖基类方法，在子Fragment中不需要处理状态栏
     */
    override fun onApplyWindowInsets(view: View, statusBarHeight: Int) {
        // 移除可能被BaseFragment添加的顶部padding
        view.setPadding(view.paddingLeft, 0, view.paddingRight, view.paddingBottom)
    }
    
    private fun setupRecyclerView() {
        adapter = SocialFeedAdapter(feedItems, this)
        binding.recyclerViewRecommended.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@RecommendedFragment.adapter
        }
    }
    
    // SocialFeedInteractionListener implementation
    override fun onItemClicked(item: SocialFeedItem) {
        // The navigation is already handled in the adapter
        // But we can add additional logic here if needed
    }

    override fun onLikeClicked(item: SocialFeedItem) {
        Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = RecommendedFragment()
    }
} 