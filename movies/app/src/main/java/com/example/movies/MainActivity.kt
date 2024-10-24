package com.example.movies

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.Decoration
import com.example.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = MovieAdapter {
        setContentView(R.layout.movie_card)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter.MovieList = Storage.movie
        binding.listMovie.adapter = adapter
        binding.listMovie.addItemDecoration(Decoration(resources))
        binding.listMovie.layoutManager = GridLayoutManager(this, 2)
    }
}