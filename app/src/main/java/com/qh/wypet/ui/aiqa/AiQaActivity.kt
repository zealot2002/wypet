package com.qh.wypet.ui.aiqa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R
import com.qh.wypet.databinding.ActivityAiQaBinding

class AiQaActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAiQaBinding
    private lateinit var chatAdapter: AiQaChatAdapter
    private val chatMessages = mutableListOf<AiMessage>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiQaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
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
            // 根据开关状态调整AI回答深度
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
        // 显示聊天区域，隐藏示例问题
        binding.recyclerViewChat.visibility = View.VISIBLE
        binding.questionsScrollView.visibility = View.GONE
        
        // 添加用户问题到聊天列表
        chatMessages.add(AiMessage(question, isUser = true))
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        binding.recyclerViewChat.scrollToPosition(chatMessages.size - 1)
        
        // 显示加载状态
        binding.progressBar.visibility = View.VISIBLE
        
        // 模拟AI回答（实际应用中应该调用AI API）
        binding.recyclerViewChat.postDelayed({
            // 隐藏加载状态
            binding.progressBar.visibility = View.GONE
            
            // 根据问题提供相关宠物营养和饲养的回答
            val answer = getPetRelatedAnswer(question)
            
            // 添加AI回答到聊天列表
            chatMessages.add(AiMessage(answer, isUser = false))
            chatAdapter.notifyItemInserted(chatMessages.size - 1)
            binding.recyclerViewChat.scrollToPosition(chatMessages.size - 1)
        }, 1000) // 模拟网络延迟
    }
    
    private fun getPetRelatedAnswer(question: String): String {
        // 这里应该根据实际情况调用AI服务，现在只是模拟一些回答
        return when {
            question.contains("营养粮") || question.contains("猫粮") || question.contains("推荐") && question.contains("猫") -> 
                "对于猫咪的日常饮食，我推荐选择以肉类蛋白为主要成分的优质全价猫粮。\n\n" +
                "幼猫（1岁以下）应选择富含蛋白质（至少30%）和脂肪（15-20%）的成长配方，如皇家幼猫粮、爱肯拿幼猫粮等。\n\n" +
                "成年猫（1-7岁）适合均衡营养的成猫粮，蛋白质含量在26-30%，脂肪含量适中，如渴望无谷猫粮、冠能成猫粮。\n\n" +
                "老年猫（7岁以上）则需要低脂肪、易消化、含有关节保健成分的老年猫粮，比如希尔斯老年猫粮。\n\n" +
                "特殊体质的猫咪，如肥胖、肾脏问题、毛球问题等，建议选择针对性处方粮。记得逐渐过渡到新猫粮，避免肠胃不适。"
            
            question.contains("猫") && (question.contains("喂食") || question.contains("饮食") || question.contains("健康")) ->
                "猫咪健康喂食的建议：\n\n" +
                "1. 定时定量：成年猫每天喂食2-3次，每次按体重和活动量计算。一般体重4kg的猫每天约需200-250卡路里。\n\n" +
                "2. 保持水分摄入：猫咪天性饮水少，考虑添加猫咪饮水机或者喂食湿粮补充水分。\n\n" +
                "3. 控制零食：零食不应超过每日总热量的10%，避免过多的威化饼干类零食。\n\n" +
                "4. 避免人类食物：巧克力、葱、蒜、葡萄等对猫有毒，乳制品可能引起腹泻。\n\n" +
                "5. 观察便便：健康的猫便便应该呈深棕色，形状规整，不过软也不过硬。\n\n" +
                "6. 定期体检：每年至少带猫咪体检一次，及时调整饮食计划。"
            
            question.contains("狗") && question.contains("健康问题") ->
                "狗狗常见健康问题及护理建议：\n\n" +
                "1. 皮肤问题：包括皮肤过敏、真菌感染、热点等，注意定期洗澡、使用宠物专用洗护产品。\n\n" +
                "2. 口腔问题：牙结石和牙龈炎常见，提供磨牙棒和定期刷牙可帮助预防。\n\n" +
                "3. 肠胃问题：腹泻、呕吐可能来源于食物变化或异物摄入，保持饮食规律很重要。\n\n" +
                "4. 关节问题：大型犬尤其容易出现关节炎，可以添加软骨素和氨糖等营养补充剂。\n\n" +
                "5. 耳部感染：定期清洁耳道，避免耳螨和细菌感染。\n\n" +
                "6. 体外寄生虫：定期驱虫，特别是春夏季节需要加强防护。\n\n" +
                "如果狗狗出现持续异常行为、食欲降低或其他不适症状，请立即咨询兽医。"
                
            question.contains("幼犬") && (question.contains("饮食") || question.contains("注意")) ->
                "幼犬饮食需要特别注意以下几点：\n\n" +
                "1. 选择专门的幼犬粮：含有高蛋白质（约28-30%）和适量脂肪，支持快速生长发育。\n\n" +
                "2. 喂食频率：2-3个月的幼犬每天需要喂食4次，3-6个月喂食3次，6个月后可降至2次。\n\n" +
                "3. 食量控制：根据体重和品种调整，避免过度喂食导致骨骼发育过快。\n\n" +
                "4. 避免人类食物：巧克力、葡萄、洋葱等对狗有毒，不要给幼犬喂食。\n\n" +
                "5. 补充适量钙质：特别是大型犬，但过量补钙可能导致骨骼发育异常。\n\n" +
                "6. 逐渐过渡：更换犬粮时，应在5-7天内逐渐混合过渡，避免肠胃不适。\n\n" +
                "7. 注意水分摄入：确保幼犬始终有新鲜的饮用水，保持充分水分。"
                
            else -> "作为宠物饲养顾问，我可以回答关于宠物营养、健康、训练和日常护理的问题。您可以更具体地询问关于您宠物的问题，比如猫粮推荐、狗狗皮肤问题、宠物行为训练等方面的问题。"
        }
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