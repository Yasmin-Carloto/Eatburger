package com.irede.eatburguer.Enums
import kotlinx.serialization.*

@Serializable
enum class FoodType {
    @SerialName("Entries")
    Entries,

    @SerialName("MainPlates")
    MainPlates,

    @SerialName("Drinks")
    Drinks,

    @SerialName("Desserts")
    Desserts
}