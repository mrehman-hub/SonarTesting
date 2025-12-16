package com.example.smartcomdemo.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://parent.api.cosmotogether.com/"
    private const val API_KEY = "Xs8pR2mGZqv3dL97NtFOJkYAbM0xWyEZHnTugC1PliSDQXKaRhfjwMUceBVo9sLdAqNzXPTGcIrkEomFyVJ5bRWZCtaLxUBHE2MdPo7wKTL"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val client = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("x-api-key", API_KEY) // Attach API key in header
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }.addInterceptor(loggingInterceptor)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

