package com.irede.eatburguer.Controllers

import com.irede.eatburguer.Model.Filter
import com.irede.eatburguer.Enums.FoodType
import com.irede.eatburguer.R

class FilterController {
    companion object{
        private var filterEntries = Filter(0, FoodType.Entries, "Entradas", R.drawable.entries_regular)
        private var filterMain = Filter(1, FoodType.MainPlates, "Principais", R.drawable.burguer_regular)
        private var filterDrinks = Filter(2, FoodType.Drinks, "Bebidas", R.drawable.drinks_regular)
        private var filterDesserts = Filter(3, FoodType.Desserts, "Sobremesas", R.drawable.desserts_regular)

        fun getFilterList() = listOf<Filter>(
            filterEntries, filterMain, filterDrinks, filterDesserts
        )

    }
}