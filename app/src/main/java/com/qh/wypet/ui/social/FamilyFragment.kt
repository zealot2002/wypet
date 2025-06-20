package com.qh.wypet.ui.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.qh.wypet.databinding.FragmentFamilyBinding
import com.qh.wypet.ui.base.BaseFragment

class FamilyFragment : BaseFragment(), SocialFeedAdapter.SocialFeedInteractionListener {

    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var adapter: SocialFeedAdapter
    private val feedItems = SampleDataProvider.getSampleFamilyData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
    }
    
    private fun setupRecyclerView() {
        adapter = SocialFeedAdapter(feedItems, this)
        binding.recyclerViewFamily.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FamilyFragment.adapter
        }
    }
    
    // SocialFeedInteractionListener implementation
    override fun onItemClicked(item: SocialFeedItem) {
        Toast.makeText(context, "查看 ${item.username} 的动态详情", Toast.LENGTH_SHORT).show()
    }

    override fun onLikeClicked(item: SocialFeedItem) {
        Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show()
    }

    override fun onCommentClicked(item: SocialFeedItem) {
        Toast.makeText(context, "评论", Toast.LENGTH_SHORT).show()
    }

    override fun onShareClicked(item: SocialFeedItem) {
        Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteClicked(item: SocialFeedItem) {
        val message = if (item.isFavorite) "收藏成功" else "已取消收藏"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FamilyFragment()
    }
} 