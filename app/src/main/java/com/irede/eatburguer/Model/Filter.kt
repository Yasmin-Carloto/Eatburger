package com.irede.eatburguer.Model

import com.irede.eatburguer.Enums.FoodType

class Filter(
    val id: Int,
    val type: FoodType,
    val typeshown: String,
    val image: Int,
    var isclicked: Boolean = false
) {

}