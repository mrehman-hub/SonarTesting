package com.example.smartcomdemo.network.models


import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("is_closed")
    val isClosed: Boolean?
)