package com.qh.wypet.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * 腾讯混元API服务接口
 */
interface HunyuanApiService {
    
    @Headers("Content-Type: application/json")
    @POST("chat/completions")
    fun chatCompletions(
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ): Call<ResponseBody>
} 