package com.example.task.utilts.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private lateinit var apiService: APIInterFace



    fun getClient(): APIInterFace {

        val clientSetup = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

        if (!::apiService.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl("https://cutie.co.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientSetup)
                .build()

            apiService = retrofit.create(APIInterFace::class.java)
        }

        return apiService
    }
}