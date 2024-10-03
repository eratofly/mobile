package com.example.workshop2

import FoodItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workshop2.databinding.ItemFoodBinding

class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view)

class FoodAdapter(
    private val onItemClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodViewHolder>() {
    var foodList = listOf<FoodItem>()

    override fun getItemCount(): Int = foodList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)

        return FoodViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val itemBinding = ItemFoodBinding.bind(holder.itemView)
        val resources = holder.itemView.resources
        val foodItem = foodList[position]

        itemBinding.name.text = foodItem.name
        itemBinding.description.text = foodItem.description
        itemBinding.price.text = resources.getString(R.string.price_template, foodItem.price)
        itemBinding.photo.setBackgroundResource(
            when (foodItem.type) {
                FoodType.BURGER -> R.drawable.burger
                FoodType.PIZZA -> R.drawable.pizza
            }
        )

        itemBinding.addToCartButton.setOnClickListener {
            onItemClick(foodItem)
        }
    }

}