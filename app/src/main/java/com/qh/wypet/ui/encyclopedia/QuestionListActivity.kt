package com.qh.wypet.ui.encyclopedia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qh.wypet.databinding.ActivityQuestionListBinding

class QuestionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionListBinding
    private lateinit var adapter: QuestionAdapter
    private var category: EncyclopediaCategory? = null

    companion object {
        private const val EXTRA_CATEGORY = "category_id"

        fun start(context: Context, category: EncyclopediaCategory) {
            val intent = Intent(context, QuestionListActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category.id)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getStringExtra(EXTRA_CATEGORY)
        if (categoryId == null) {
            finish()
            return
        }

        // Find the category from the ID
        category = EncyclopediaDataProvider.getCategories().find { it.id == categoryId }
        
        setupToolbar()
        setupRecyclerView()
        loadQuestions(categoryId)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        
        // Set the title to the category name
        category?.let {
            binding.toolbarTitle.text = it.name
        }
    }

    private fun setupRecyclerView() {
        adapter = QuestionAdapter { question ->
            // Here you can handle question click, e.g., show detailed answer
            // For now, we'll just show the content in a dialog
            showQuestionDetailDialog(question)
        }
        
        binding.questionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.questionsRecyclerView.adapter = adapter
    }
    
    private fun loadQuestions(categoryId: String) {
        val questions = EncyclopediaDataProvider.getQuestionsByCategory(categoryId)
        
        if (questions.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.emptyView.visibility = View.GONE
            adapter.submitList(questions)
        }
        
        binding.loadingView.visibility = View.GONE
    }
    
    private fun showQuestionDetailDialog(question: Question) {
        // In a real app, you might want to show this in a bottom sheet or new activity
        // For simplicity, we'll just use a dialog here
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(question.title)
            .setMessage(question.content)
            .setPositiveButton("关闭") { dialog, _ -> dialog.dismiss() }
            .show()
    }
} 