package com.example.task.utilts.api

import com.example.task.ResponseTamil
import retrofit2.http.GET

interface APIInterFace {
    @GET("socialmedia/mob_app/apis/interview_api.php?language=english")
     fun details():retrofit2.Call<ResponseTamil?>?

    @GET("socialmedia/mob_app/apis/interview_api.php?language=tamil")
    fun tamilDetails():retrofit2.Call<ResponseTamil?>?

    @GET("socialmedia/mob_app/apis/interview_api.php?language=hindi")
    fun hindiDetails():retrofit2.Call<ResponseTamil?>?
}

