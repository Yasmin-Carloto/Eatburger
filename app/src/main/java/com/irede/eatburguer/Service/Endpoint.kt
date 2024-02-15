package com.irede.eatburguer.Service

import com.google.gson.JsonArray
import retrofit2.http.GET

interface Endpoint {
    @GET("food")
    fun getFoodJson(): retrofit2.Call<JsonArray>
}