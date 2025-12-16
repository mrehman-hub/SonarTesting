package com.example.smartcomdemo.network.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("details")
    val details: Details?,
    @SerializedName("event")
    val event: String?
)