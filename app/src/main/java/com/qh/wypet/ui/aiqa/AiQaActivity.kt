package com.qh.wypet.ui.aiqa

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R
import com.qh.wypet.api.HunyuanApiClient
import com.qh.wypet.databinding.ActivityAiQaBinding

class AiQaActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAiQaBinding
    private lateinit var chatAdapter: AiQaChatAdapter
    private val chatMessages = mutableListOf<AiMessage>()
    private lateinit var apiClient: HunyuanApiClient
    private var isDeepThinkingEnabled = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiQaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        apiClient = HunyuanApiClient()
        
        setupToolbar()
        setupRecyclerView()
        setupInputActions()
        setupSampleQuestions()
    }
    
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        chatAdapter = AiQaChatAdapter(chatMessages)
        binding.recyclerViewChat.apply {
            layoutManager = LinearLayoutManager(this@AiQaActivity, RecyclerView.VERTICAL, false)
            adapter = chatAdapter
        }
    }
    
    private fun setupInputActions() {
        binding.sendButton.setOnClickListener {
            sendQuestion()
        }
        
        binding.editTextQuestion.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendQuestion()
                true
            } else {
                false
            }
        }
    }
    
    private fun setupSampleQuestions() {
        val sampleQuestions = listOf(
            "推荐适合猫咪的营养粮？",
            "猫咪应该怎么喂食才健康？",
            "狗狗有哪些常见的健康问题？",
            "幼犬饮食需要注意什么？"
        )
        
        // 设置示例问题
        binding.sampleQuestion1.text = sampleQuestions[0]
        binding.sampleQuestion2.text = sampleQuestions[1]
        binding.sampleQuestion3.text = sampleQuestions[2]
        binding.sampleQuestion4.text = sampleQuestions[3]
        
        // 设置点击事件
        binding.sampleQuestion1.setOnClickListener { askQuestion(sampleQuestions[0]) }
        binding.sampleQuestion2.setOnClickListener { askQuestion(sampleQuestions[1]) }
        binding.sampleQuestion3.setOnClickListener { askQuestion(sampleQuestions[2]) }
        binding.sampleQuestion4.setOnClickListener { askQuestion(sampleQuestions[3]) }
        
        // 深度思考开关
        binding.deepThinkingSwitch.setOnCheckedChangeListener { _, isChecked ->
            isDeepThinkingEnabled = isChecked
            Toast.makeText(this, if (isChecked) "已开启深度思考" else "已关闭深度思考", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun sendQuestion() {
        val question = binding.editTextQuestion.text.toString().trim()
        if (question.isNotEmpty()) {
            askQuestion(question)
            binding.editTextQuestion.setText("")
        }
    }
    
    private fun askQuestion(question: String) {
        // 检查网络连接
        if (!isNetworkConnected()) {
            Toast.makeText(this, "网络连接不可用，请检查网络设置", Toast.LENGTH_LONG).show()
            return
        }
        
        // 显示聊天区域，隐藏示例问题
        binding.recyclerViewChat.visibility = View.VISIBLE
        binding.questionsScrollView.visibility = View.GONE
        
        // 添加用户问题到聊天列表
        chatMessages.add(AiMessage(question, isUser = true))
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        binding.recyclerViewChat.scrollToPosition(chatMessages.size - 1)
        
        // 显示加载状态
        binding.progressBar.visibility = View.VISIBLE
        
        // 调用混元API获取回答
        apiClient.sendChatRequest(question, object : HunyuanApiClient.HunyuanResponseListener {
            override fun onSuccess(response: String) {
                runOnUiThread {
                    // 隐藏加载状态
                    binding.progressBar.visibility = View.GONE
                    
                    // 添加AI回答到聊天列表
                    chatMessages.add(AiMessage(response, isUser = false))
                    chatAdapter.notifyItemInserted(chatMessages.size - 1)
                    binding.recyclerViewChat.scrollToPosition(chatMessages.size - 1)
                }
            }
            
            override fun onError(errorMessage: String) {
                runOnUiThread {
                    // 隐藏加载状态
                    binding.progressBar.visibility = View.GONE
                    
                    // 显示错误信息
                    Toast.makeText(this@AiQaActivity, "获取回答失败: $errorMessage", Toast.LENGTH_LONG).show()
                    
                    // 添加一个默认回答表示错误
                    val fallbackAnswer = "抱歉，我暂时无法回答这个问题。请稍后再试。"
                    chatMessages.add(AiMessage(fallbackAnswer, isUser = false))
                    chatAdapter.notifyItemInserted(chatMessages.size - 1)
                    binding.recyclerViewChat.scrollToPosition(chatMessages.size - 1)
                }
            }
        })
    }
    
    // 检查网络连接状态
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.recyclerViewChat.visibility == View.VISIBLE && chatMessages.isNotEmpty()) {
            // 返回到问题列表视图
            binding.recyclerViewChat.visibility = View.GONE
            binding.questionsScrollView.visibility = View.VISIBLE
            // 清空聊天记录
            chatMessages.clear()
            chatAdapter.notifyDataSetChanged()
        } else {
            super.onBackPressed()
        }
    }
    
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AiQaActivity::class.java))
        }
    }
} 