package com.example.cookieclicker

import ClickerViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookieclicker.databinding.BuildingBinding
import kotlin.math.pow


class BuildingDiffCallback : DiffUtil.ItemCallback<Building>() {
    override fun areItemsTheSame(oldItem: Building, newItem: Building): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Building, newItem: Building): Boolean {
        return oldItem.state == newItem.state
    }
}

class BuildingAdapter(private val viewModel: ClickerViewModel) : ListAdapter<Building, BuildingAdapter.BuildingViewHolder>(BuildingDiffCallback()) {
    class BuildingViewHolder(private val binding: BuildingBinding, private val viewModel: ClickerViewModel) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(building: Building) {
            binding.name.text = building.name
            binding.cost.text = building.cost.toLong().toString()
            binding.quantity.text = building.quantity.toString()
            Glide.with(binding.root.context)
                .load(building.imageURL)
                .into(binding.photo)

            println(building)

            if (building.state == BuildingState.AVAILABLE)
            {
                binding.background.isVisible = false
                binding.card.setOnClickListener {
                   viewModel.buyBuilding(building)
                }

            }
            else if (building.state == BuildingState.UNAVAILABLE) {
                binding.background.isVisible = true
                binding.card.setOnClickListener {
                    Toast.makeText(itemView.context, "Вы не можете купить ${building.name}. Кликайте, пожалуйста!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val binding = BuildingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildingViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}