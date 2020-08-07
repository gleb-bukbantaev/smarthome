package com.example.myapplication.web

import com.example.myapplication.data.HumidityState
import com.example.myapplication.data.LightState
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("get/light/state")
    suspend fun getLightState():LightState
    @POST("post/light/state")
    suspend fun getLightState(state:LightState)
    @GET("get/humidity/state")
    suspend fun getHumidityState():HumidityState
    @POST("post/humidity/state")
    suspend fun postHumidityState(state:HumidityState)
}