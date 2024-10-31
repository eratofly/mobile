package com.example.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieCardBinding

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: MovieCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movieTitle.text = intent.getSerializableExtra("name").toString()
        binding.movieDescription.text = intent.getSerializableExtra("annotation").toString()
        binding.movieRating.text = intent.getSerializableExtra("rating").toString()
        Glide.with(this)
            .load(intent.getSerializableExtra("previewURL").toString())
            .into(binding.movieImage)

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}