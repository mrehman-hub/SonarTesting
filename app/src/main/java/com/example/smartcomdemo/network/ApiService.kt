package com.example.smartcomdemo.network

import com.example.smartcomdemo.network.models.BaseResponse
import com.example.smartcomdemo.network.models.BaseResponse2
import com.example.smartcomdemo.network.models.RequestBody2
import com.example.smartcomdemo.network.models.RequestBodyData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("parent-app-logs/whats-new/modal/init")
    fun getUrl(@Body request: RequestBodyData): Call<BaseResponse>

    @POST("parent-app-logs/whats-new/modal/event")
    fun onPopupDismiss(@Body request: RequestBody2): Call<BaseResponse2>
}