package com.example.myapplication.web

import com.example.myapplication.data.HumidityState
import com.example.myapplication.data.LightState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("get/light/state")
    suspend fun getLightState():LightState
    @POST("set/light/state")
    suspend fun setLightState(@Body state:LightState)
    @GET("get/humidity/state")
    suspend fun getHumidityState():HumidityState
    @POST("set/humidity/state")
    suspend fun setHumidityState(state:HumidityState)
}