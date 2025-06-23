package com.qh.wypet.ui.aiqa

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R
import com.qh.wypet.api.HunyuanApiClient
import com.qh.wypet.databinding.ActivityAiQaBinding
import java.util.UUID
import java.util.Random
import java.util.Collections

class AiQaActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAiQaBinding
    private lateinit var chatAdapter: AiQaChatAdapter
    private val chatMessages = Collections.synchronizedList(mutableListOf<AiMessage>())
    private lateinit var apiClient: HunyuanApiClient
    private var isDeepThinkingEnabled = false
    private val handler = Handler(Looper.getMainLooper())
    private val random = Random()
    
    // AI思考中的占位消息ID
    private val THINKING_MESSAGE_ID = "thinking_placeholder"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiQaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        apiClient = HunyuanApiClient()
        
        setupToolbar()
        setupRecyclerView()
        setupInputActions()
        setupSampleQuestions()
        setupKeyboardBehavior()
    }
    
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        chatAdapter = AiQaChatAdapter(chatMessages)
        binding.recyclerViewChat.apply {
            layoutManager = LinearLayoutManager(this@AiQaActivity).apply {
            }
            adapter = chatAdapter
            // 防止RecyclerView在更新时闪烁
            itemAnimator = null
        }
    }
    
    private fun setupInputActions() {
        binding.editTextQuestion.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 当输入框获取焦点时，滚动到底部确保输入框可见
                handler.postDelayed({
                    scrollToLatestMessage()
                }, 300)
            }
        }
        
        binding.sendButton.setOnClickListener {
            hideKeyboard()
            sendQuestion()
        }
        
        binding.editTextQuestion.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideKeyboard()
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
    
    private fun setupKeyboardBehavior() {
        // 点击输入框外的区域收起键盘
        binding.recyclerViewChat.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        
        // 发送后收起键盘
        binding.sendButton.setOnClickListener {
            hideKeyboard()
            sendQuestion()
        }
    }
    
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextQuestion.windowToken, 0)
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
        val userMessage = AiMessage(
            content = question,
            isUser = true,
            messageId = UUID.randomUUID().toString()
        )
        
        runOnUiThread {
            chatAdapter.addMessage(userMessage)
            scrollToLatestMessage()
            
            // 添加AI思考中的效果
            showAiThinking()
        }
        
        // 延迟一段时间后再发送请求，模拟思考时间
        val thinkingTime = if (isDeepThinkingEnabled) {
            random.nextInt(2000) + 2000 // 深度思考：2-4秒
        } else {
            random.nextInt(1000) + 1000 // 普通思考：1-2秒
        }
        
        handler.postDelayed({
            // 调用混元API获取回答
            apiClient.sendChatRequest(question, object : HunyuanApiClient.HunyuanResponseListener {
                override fun onSuccess(response: String) {
                    runOnUiThread {
                        try {
                            // 移除思考中的动画
                            removeAiThinking()
                            
                            // 添加AI回答到聊天列表
                            val aiMessage = AiMessage(
                                content = response,
                                isUser = false,
                                messageId = UUID.randomUUID().toString()
                            )
                            chatAdapter.addMessage(aiMessage)
                            scrollToLatestMessage()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this@AiQaActivity, "更新UI时出错: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                
                override fun onError(errorMessage: String) {
                    runOnUiThread {
                        try {
                            // 移除思考中的动画
                            removeAiThinking()
                            
                            // 显示错误信息
                            Toast.makeText(this@AiQaActivity, "获取回答失败: $errorMessage", Toast.LENGTH_LONG).show()
                            
                            // 添加一个默认回答表示错误
                            val fallbackAnswer = "抱歉，我暂时无法回答这个问题。请稍后再试。"
                            val errorMessage = AiMessage(
                                content = fallbackAnswer,
                                isUser = false,
                                messageId = UUID.randomUUID().toString()
                            )
                            chatAdapter.addMessage(errorMessage)
                            scrollToLatestMessage()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this@AiQaActivity, "更新UI时出错: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }, thinkingTime + 0L)
    }
    
    // 显示AI思考中动画
    private fun showAiThinking() {
        try {
            // 隐藏加载状态圈
            binding.progressBar.visibility = View.GONE
            
            // 添加思考中的消息
            val thinkingMessage = AiMessage("思考中...", isUser = false, messageId = THINKING_MESSAGE_ID)
            chatAdapter.addMessage(thinkingMessage)
            scrollToLatestMessage()
            
            // 开始思考动画
            animateThinking()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "显示思考动画时出错: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    // 移除AI思考中动画
    private fun removeAiThinking() {
        try {
            // 停止思考动画
            handler.removeCallbacksAndMessages(null)
            
            // 移除思考中消息
            val thinkingPosition = chatAdapter.findMessagePositionById(THINKING_MESSAGE_ID)
            if (thinkingPosition != -1) {
                chatAdapter.removeMessage(thinkingPosition)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // 即使移除失败也继续执行
        }
    }
    
    // 思考动画效果
    private fun animateThinking() {
        val dots = arrayOf("思考中", "思考中.", "思考中..", "思考中...")
        var index = 0
        
        val runnable = object : Runnable {
            override fun run() {
                try {
                    val thinkingPosition = chatAdapter.findMessagePositionById(THINKING_MESSAGE_ID)
                    if (thinkingPosition != -1) {
                        val updatedMessage = AiMessage("${dots[index]}", isUser = false, messageId = THINKING_MESSAGE_ID)
                        chatAdapter.updateMessage(thinkingPosition, updatedMessage)
                        index = (index + 1) % dots.size
                        handler.postDelayed(this, 500)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // 出错时停止动画
                    handler.removeCallbacksAndMessages(null)
                }
            }
        }
        
        handler.post(runnable)
    }
    
    // 滚动到最新消息
    private fun scrollToLatestMessage() {
        try {
            if (chatAdapter.itemCount > 0) {
                binding.recyclerViewChat.post {
                    binding.recyclerViewChat.smoothScrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            
            // 停止所有动画和后台任务
            handler.removeCallbacksAndMessages(null)
            
            // 清空聊天记录
            synchronized(chatMessages) {
                chatMessages.clear()
                chatAdapter.replaceAllMessages(emptyList())
            }
        } else {
            super.onBackPressed()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // 确保清理所有挂起的操作
        handler.removeCallbacksAndMessages(null)
    }
    
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AiQaActivity::class.java))
        }
    }
} 