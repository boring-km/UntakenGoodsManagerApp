package com.emart24.service

import com.emart24.model.UnTakenGoods
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetroService {

    @POST("register")
    fun registerGoods(@Body unTakenGoods: UnTakenGoods): Call<Boolean>
}