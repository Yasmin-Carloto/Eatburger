package com.irede.eatburguer.Controllers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.JsonArray
import com.irede.eatburguer.Model.Food
import com.irede.eatburguer.Service.Endpoint
import com.irede.eatburguer.Util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.Response

class FoodController {

    companion object{
        private fun getFoodJson(): MutableList<String>? {
            var responseData: MutableList<String>? = null
            val retrofitClient = NetworkUtils.getRetrofitInstance(path = "http://35.208.255.221/")
            val endpoint = retrofitClient.create(Endpoint::class.java)

            runBlocking {
                withContext(Dispatchers.IO) {
                    val response: Response<JsonArray> = endpoint.getFoodJson().execute()
                    if (response.isSuccessful) {
                        responseData = mutableListOf()
                        response.body()?.forEach {
                            responseData?.add(it.toString())
                        }
                    }
                }
            }

            return responseData
        }

        fun getFoodList(): List<Food>{
            var responseData = getFoodJson()
            val menu = Json.decodeFromString<List<Food>>(responseData.toString() ?: "[]")
            Log.d("getFoodList", menu.toString())
            return menu
        }

        fun getPriceOfSelectedItems(sharedPreferences: SharedPreferences, context: Context): String{
            val menu = getFoodList()
            var totalPrice = 0.0

            for (key in sharedPreferences.all.keys) {

                if (key.startsWith("FoodCheckBox")) {

                    val foodId = key.substringAfter("FoodCheckBox").toInt()
                    val isSelected: Boolean = sharedPreferences.getBoolean(key, false)

                    if (isSelected) {
                        val food = menu.find { it.id == foodId }
                        food?.let {
                            totalPrice += it.price
                        }
                    }
                }
            }
            val totalPriceFormated = String.format("%.2f", totalPrice)
            return totalPriceFormated
        }
    }

}