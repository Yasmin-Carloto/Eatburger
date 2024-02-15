package com.irede.eatburguer.Adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.irede.eatburguer.Model.Food
import com.irede.eatburguer.R

class AdapterFood(
    private val menu: MutableList<Food>,
    private val goToInfoPage: (food: Food) -> Unit,
    private val getPrice: () -> Unit,
    ) : RecyclerView.Adapter<AdapterFood.MyViewHolder>() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        sharedPreferences = parent.context.getSharedPreferences("FoodCheckBoxState", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_food, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = menu.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val foodCard: Food = menu[position]
        holder.bind(foodCard)

        val isChecked = sharedPreferences.getBoolean("FoodCheckBox${foodCard.id}", false)
        holder.foodCheckBox.isChecked = isChecked

        holder.itemView.setOnClickListener{
            goToInfoPage(foodCard)
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodImage: ImageView = itemView.findViewById(R.id.image_food_card)
        val foodName: TextView = itemView.findViewById(R.id.text_name_food_card)
        val foodTime: TextView = itemView.findViewById(R.id.text_wait_time_food_card)
        val foodPrice: TextView = itemView.findViewById(R.id.text_price_food_card)
        val foodCheckBox: CheckBox = itemView.findViewById(R.id.checkbox_food_card)

        init {
            foodCheckBox.setOnCheckedChangeListener { _, ischecked ->
                val foodCard = menu[adapterPosition]
                foodCard.ischecked = ischecked
                sharedPreferences.edit().putBoolean("FoodCheckBox${foodCard.id}", ischecked).apply()

                if(ischecked){
                    foodCheckBox.setBackgroundResource(R.drawable.bg_fixed_button)
                    foodCheckBox.setTextColor(ContextCompat.getColor(itemView.context,
                        R.color.white
                    ))
                    foodCheckBox.setText(R.string.text_added)
                }else{
                    foodCheckBox.setBackgroundResource(R.drawable.bg_checkbox)
                    foodCheckBox.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                    foodCheckBox.setText(R.string.text_add)
                }

                sharedPreferences.edit().apply {
                    putBoolean("FoodCheckBox${foodCard.id}", ischecked)
                    apply()
                }

                getPrice()
            }
        }

        fun bind(foodCard: Food) {
            foodName.text = foodCard.name
            val imageId = itemView.context.resources.getIdentifier(foodCard.image, "drawable", itemView.context.packageName)
            foodImage.setImageResource(imageId)
            foodTime.text = "${foodCard.time.toString()}min"
            foodPrice.text = "R$ ${foodCard.price.toString()}"

            foodCheckBox.isChecked = sharedPreferences.getBoolean("FoodCheckBox${foodCard.id}", false)
        }
    }

}