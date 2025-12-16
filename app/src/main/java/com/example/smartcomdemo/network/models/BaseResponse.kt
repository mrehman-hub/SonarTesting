package com.example.smartcomdemo.network.models


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("finish_url")
    val finishUrl: String?,
    @SerializedName("product_url")
    val productUrl: String?,
    @SerializedName("redirect_url")
    val redirectUrl: String?,
    @SerializedName("token")
    val token: Token?
)