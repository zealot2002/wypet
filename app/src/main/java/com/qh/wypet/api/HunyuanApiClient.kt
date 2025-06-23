package com.qh.wypet.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 混元AI API客户端
 */
class HunyuanApiClient {
    private val TAG = "HunyuanApiClient"
    private val BASE_URL = "https://api.hunyuan.cloud.tencent.com/v1/"
    private val API_KEY = "sk-tztTPwKxczwIc45xsREdd681HbL52BjroO9w3wMD5XLS598i"
    private val apiService: HunyuanApiService
    
    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        apiService = retrofit.create(HunyuanApiService::class.java)
    }
    
    // 添加此方法用于打印请求详情，辅助调试
    private fun logFullRequestDetails(model: String, messages: ArrayList<JsonObject>, requestJson: String) {
        Log.d(TAG, "==== 混元API请求详情 ====")
        Log.d(TAG, "模型: $model")
        Log.d(TAG, "消息数: ${messages.size}")
        for (i in messages.indices) {
            val msg = messages[i]
            Log.d(TAG, "消息[$i] - 角色: ${msg.get("role").asString}, 内容长度: ${msg.get("content").asString.length}")
        }
        Log.d(TAG, "完整请求JSON: $requestJson")
        Log.d(TAG, "==== 请求详情结束 ====")
    }
    
    /**
     * 发送聊天请求到混元AI API
     * @param question 用户提问
     * @param listener 回调监听器
     */
    fun sendChatRequest(question: String, listener: HunyuanResponseListener) {
        try {
            // 创建消息数组
            val messagesArray = ArrayList<JsonObject>()
            
            // 系统提示消息
            val systemMessage = JsonObject()
            systemMessage.addProperty("role", "system")
            systemMessage.addProperty("content", "你是一个专业的宠物饲养顾问，擅长回答关于宠物营养、健康、训练和日常护理的问题。回答要专业、友好、易于理解。")
            messagesArray.add(systemMessage)
            
            // 用户问题消息
            val userMessage = JsonObject()
            userMessage.addProperty("role", "user")
            userMessage.addProperty("content", question)
            messagesArray.add(userMessage)
            
            // 创建请求对象，按照官方文档定义参数
            val modelName = "hunyuan-lite"  // 使用轻量级模型以提高响应速度
            val requestObject = JsonObject()
            requestObject.addProperty("model", modelName)
            requestObject.add("messages", Gson().toJsonTree(messagesArray))
            requestObject.addProperty("temperature", 0.7)
            requestObject.addProperty("top_p", 0.95)
            // 不使用enable_enhancement以加快响应
            
            val requestJson = Gson().toJson(requestObject)
            logFullRequestDetails(modelName, messagesArray, requestJson)
            
            val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())
            val call = apiService.chatCompletions("Bearer $API_KEY", requestBody)
            
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()?.string()
                        Log.d(TAG, "Response: $responseBody")
                        try {
                            val jsonObject = JSONObject(responseBody)
                            
                            // 检查是否有错误
                            if (jsonObject.has("error")) {
                                val error = jsonObject.getJSONObject("error")
                                val errorMessage = error.getString("message")
                                val errorCode = error.getInt("code")
                                listener.onError("API错误 (代码:$errorCode): $errorMessage")
                                return
                            }
                            
                            // 正常处理响应 - 根据文档格式解析
                            if (jsonObject.has("choices") && jsonObject.getJSONArray("choices").length() > 0) {
                                val choices = jsonObject.getJSONArray("choices")
                                val firstChoice = choices.getJSONObject(0)
                                
                                // 根据文档，正确的消息格式应该是在message对象中
                                if (firstChoice.has("message")) {
                                    val message = firstChoice.getJSONObject("message")
                                    val content = message.getString("content")
                                    listener.onSuccess(content)
                                } else if (firstChoice.has("delta") && firstChoice.getJSONObject("delta").has("content")) {
                                    // 流式输出的格式
                                    val content = firstChoice.getJSONObject("delta").getString("content")
                                    listener.onSuccess(content)
                                } else {
                                    listener.onError("API响应格式异常：缺少消息内容")
                                }
                            } else {
                                listener.onError("API返回了空的选择列表")
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "解析响应失败: ${e.message}", e)
                            listener.onError("解析响应失败: ${e.message}")
                        }
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "未知错误"
                        Log.e(TAG, "API请求失败: $errorBody")
                        listener.onError("API请求失败: $errorBody")
                    }
                }
                
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(TAG, "API请求异常: ${t.message}")
                    listener.onError("API请求异常: ${t.message}")
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "发送请求异常: ${e.message}")
            listener.onError("发送请求异常: ${e.message}")
        }
    }
    
    /**
     * 混元API响应监听器接口
     */
    interface HunyuanResponseListener {
        fun onSuccess(response: String)
        fun onError(errorMessage: String)
    }
}

/**
 * 混元聊天请求模型
 */
data class HunyuanChatRequest(
    val model: String = "hunyuan-pro",
    val messages: List<ChatMessage>,
    val temperature: Double = 0.7,
    @SerializedName("top_p") val topP: Double = 0.95,
    val stream: Boolean = false
)

/**
 * 聊天消息模型
 */
data class ChatMessage(
    val role: String, // "system", "user", "assistant"
    val content: String
) 