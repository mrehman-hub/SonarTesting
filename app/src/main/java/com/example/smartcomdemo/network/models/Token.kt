package com.example.smartcomdemo.network.models


import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("created_on")
    val createdOn: String?,
    @SerializedName("expire_on")
    val expireOn: String?,
    @SerializedName("token")
    val token: String?
)