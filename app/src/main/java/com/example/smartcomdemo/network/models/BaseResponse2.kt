package com.example.smartcomdemo.network.models


import com.google.gson.annotations.SerializedName

data class BaseResponse2(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)