package com.irede.eatburguer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.irede.eatburguer.Adapter.AdapterFilter
import com.irede.eatburguer.Adapter.AdapterFood
import com.irede.eatburguer.Model.Food
import com.irede.eatburguer.Controllers.FilterController
import com.irede.eatburguer.Controllers.FoodController
import com.irede.eatburguer.databinding.ActivityMainPageBinding

class MainPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initRecyclerViewFilter()

        binding.buttonFinishPurchase.setOnClickListener{
            var price = FoodController.getPriceOfSelectedItems(getSharedPreference(), this).toDouble()
            if (price == 0.0){
                Toast.makeText(this, "Selecione pelo menos um prato.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Seu pedido foi enviado para o balcão do restaurante.", Toast.LENGTH_LONG).show()
                deleteSharedPreferences(this)
                restartApp()
            }
        }

    }

    private fun initRecyclerViewFood(filteredFoodList: MutableList<Food>) {
        val layoutManager = GridLayoutManager(this, 2)

        binding.linearContainerFoodCards.layoutManager = layoutManager
        binding.linearContainerFoodCards.setHasFixedSize(true)

        binding.linearContainerFoodCards.adapter =  AdapterFood(filteredFoodList, goToInfoPage()) {
            binding.textTotalPrice.text = "R$${FoodController.getPriceOfSelectedItems(getSharedPreference(), this)}"
        }
    }

    private fun initRecyclerViewFilter(){
        val layoutManager = GridLayoutManager(this, 2)
        binding.linearContainerFilters.layoutManager = layoutManager
        binding.linearContainerFilters.setHasFixedSize(true)
        binding.linearContainerFilters.adapter = AdapterFilter(FilterController.getFilterList(), FoodController.getFoodList()) { foods, type ->
            var filteredFoodList = mutableListOf<Food>()
            for (food in foods){
                if(food.type === type){
                    filteredFoodList.add(food)
                }
            }
            initRecyclerViewFood(filteredFoodList)
        }
    }

    private fun getSharedPreference(): SharedPreferences{
        return getSharedPreferences("FoodCheckBoxState", Context.MODE_PRIVATE)
    }

    private fun goToInfoPage(): (food: Food) -> Unit{
        return fun (food: Food){
            val navigate = Intent(this, FoodViewActivity::class.java).apply {
                putExtra("food_id", food.id)
                putExtra("food_isChecked",food.ischecked)
                putExtra("food_name", food.name)
                putExtra("food_image", food.image)
                putExtra("food_price", food.price)
                putExtra("food_time", food.time)
                putExtra("food_description", food.description)
            }
            startActivity(navigate)
        }
    }

    private fun deleteSharedPreferences(context: Context){
        var sharedPreferences = context.getSharedPreferences("FoodCheckBoxState", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun restartApp() {
        FilterController.getFilterList().forEach { it.isclicked = false }
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}