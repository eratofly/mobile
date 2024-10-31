package com.example.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.Decoration
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.databinding.MovieCardBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = MovieAdapter {
        val intent = Intent(this, MovieActivity::class.java);
        intent.putExtra("previewURL", it.previewURL)
        intent.putExtra("name", it.name)
        intent.putExtra("annotation", it.annotation)
        intent.putExtra("rating", it.rating)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listMovie.adapter = adapter
        adapter.movieList = Storage.movie
        adapter.notifyDataSetChanged()

        binding.listMovie.adapter = adapter
        binding.listMovie.addItemDecoration(Decoration(resources))
        binding.listMovie.layoutManager = GridLayoutManager(this, 2)
    }
}