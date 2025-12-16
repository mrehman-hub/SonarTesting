package com.example.smartcomdemo.network.models


import com.google.gson.annotations.SerializedName

data class RequestBody2(
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: String?
)