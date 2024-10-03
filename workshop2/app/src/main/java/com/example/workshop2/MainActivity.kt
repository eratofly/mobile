package com.example.workshop2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.workshop2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = FoodAdapter {
        Toast.makeText(this, "${it.name} добавлен в корзину", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listView.adapter = adapter
        adapter.foodList = Storage.food
        adapter.notifyDataSetChanged()

         binding.listView.adapter = adapter
         binding.listView.layoutManager = GridLayoutManager(this, 2)
    }
}