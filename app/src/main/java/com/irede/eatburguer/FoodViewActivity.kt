package com.irede.eatburguer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.irede.eatburguer.Controllers.FilterController
import com.irede.eatburguer.databinding.ActivityFoodViewBinding

class FoodViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodViewBinding
    private lateinit var sharedPreferencesFood: SharedPreferences
    private lateinit var sharedPreferencesValue: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val resources = resources

        sharedPreferencesFood = getSharedPreferences("FoodCheckBoxState", Context.MODE_PRIVATE)
        sharedPreferencesValue = getSharedPreferences("EditTextValue", Context.MODE_PRIVATE)

        val foodId = intent.getIntExtra("food_id", 0)
        val foodIsChecked = sharedPreferencesFood.getBoolean("FoodCheckBox$foodId", false)
        val foodValue = sharedPreferencesValue.getString("EditTextValue$foodId", "")

        binding.checkboxFoodView.isChecked = foodIsChecked

        if(foodIsChecked){
            binding.editFoodPreferences.setText(foodValue.orEmpty())
        }

        val foodName = intent.getStringExtra("food_name")
        val foodPrice = intent.getFloatExtra("food_price", 0.0f)
        val foodImage = intent.getStringExtra("food_image")
        val imageId = resources.getIdentifier(foodImage, "drawable", packageName)
        val foodTime = intent.getIntExtra("food_time", 0)
        val foodDescription = intent.getStringExtra("food_description")

        updateView(foodIsChecked, binding.checkboxFoodView)

        bind(foodName, foodPrice, imageId, foodTime, foodDescription)
        binding.checkboxFoodView.setOnCheckedChangeListener { _, isChecked ->
            FilterController.getFilterList().forEach { it.isclicked = false }

            if(isChecked){
                sharedPreferencesValue.edit().putString("EditTextValue$foodId", binding.editFoodPreferences.text.toString()).apply()
                binding.editFoodPreferences.setText(foodValue.toString())
            }
            sharedPreferencesFood.edit().putBoolean("FoodCheckBox$foodId", isChecked).apply()

            val navigateMainPage = Intent(this, MainPageActivity::class.java)
            startActivity(navigateMainPage)
        }

        binding.imageBack.setOnClickListener{
            FilterController.getFilterList().forEach { it.isclicked = false }
            val navigateMainPage = Intent(this, MainPageActivity::class.java)
            startActivity(navigateMainPage)
        }
    }

    private fun bind(foodName: String?, foodPrice: Float, foodImage: Int, foodTime: Int, foodDescription: String?){
        val nameTextView: TextView = findViewById(R.id.text_food_name)
        val priceTextView: TextView = findViewById(R.id.text_food_price)
        val imageView: ImageView = findViewById(R.id.image_food)
        val timeTextView: TextView = findViewById(R.id.text_time)
        val descriptionTextView: TextView = findViewById(R.id.text_description)

        nameTextView.text = foodName
        priceTextView.text = "R$${foodPrice}"
        imageView.setBackgroundResource(foodImage)
        timeTextView.text = "${foodTime}min"
        descriptionTextView.text = foodDescription
    }

    private fun updateView(show: Boolean, checkBox: CheckBox){
        if(show){
            checkBox.setBackgroundResource(R.drawable.bg_fixed_button)
            checkBox.setTextColor(getResources().getColor(R.color.white))
            checkBox.setText(R.string.text_added)
        }else{
            checkBox.setBackgroundResource(R.drawable.bg_checkbox)
            checkBox.setTextColor(getResources().getColor(R.color.gray))
            checkBox.setText(R.string.text_add)
        }
    }

}