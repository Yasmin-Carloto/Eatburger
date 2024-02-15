package com.irede.eatburguer.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irede.eatburguer.Model.Filter
import com.irede.eatburguer.Model.Food
import com.irede.eatburguer.Enums.FoodType
import com.irede.eatburguer.R

class AdapterFilter(
    private val filters: List<Filter>,
    private val foodList: List<Food>,
    private val filteredPlates: (foods: List<Food>, type: FoodType) -> Unit
) : RecyclerView.Adapter<AdapterFilter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_filter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = filters.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val filterCard: Filter = filters[position]

        holder.filterImage.setImageResource(filterCard.image)
        holder.filterText.text = filterCard.typeshown

        if (filterCard.isclicked) {
            holder.filterContainer.setBackgroundResource(R.drawable.bg_food_type_text_clicked)
        } else {
            holder.filterContainer.setBackgroundResource(R.drawable.bg_food_type_text)
        }

        holder.filterContainer.setOnClickListener {
            filterCard.isclicked = true
            notifyDataSetChanged()
            filters.forEachIndexed { index, item ->
                if (index != position) {
                    item.isclicked = false
                }
            }
            filteredPlates(foodList, filterCard.type)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filterImage: ImageView = itemView.findViewById(R.id.image_card_filter)
        val filterText: TextView = itemView.findViewById(R.id.text_card_filter)
        val filterContainer: LinearLayout = itemView.findViewById(R.id.card_container)
    }
}
