package com.irede.eatburguer.Model
import com.irede.eatburguer.Enums.FoodType

@kotlinx.serialization.Serializable
data class Food(
    val id: Int,
    val name: String,
    val image: String,
    val price: Float,
    val time: Int,
    val description: String,
    val type: FoodType,
    var ischecked: Boolean = false
) {}