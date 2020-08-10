package com.example.myapplication.web

import com.example.myapplication.data.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("get/menu/state")
    suspend fun getMenuState(): MenuState
    @GET("get/light/state")
    suspend fun getLightState():LightState
    @POST("set/light/state")
    suspend fun setLightState(@Body state:LightState)
    @GET("get/temperature/state")
    suspend fun getTemperatureInsideState():TemperatureInsideState
    @POST("set/temperature/state")
    suspend fun setTemperatureInsideState(@Body state:TemperatureInsideState)
    @GET("get/humidity/state")
    suspend fun getHumidityState():HumidityState
    @POST("set/humidity/state")
    suspend fun setHumidityState(@Body state:HumidityState)
    @GET("get/lock/state")
    suspend fun getLockState():LockState
    @POST("set/lock/state")
    suspend fun setLockState(@Body state:LockState)
    @GET("get/camera/state")
    suspend fun getCameraState():CameraState
    @GET("get/history")
    suspend fun getHistory(): History
    @GET("get/lock/history")
    suspend fun getLockHistory(): LockHistory

}