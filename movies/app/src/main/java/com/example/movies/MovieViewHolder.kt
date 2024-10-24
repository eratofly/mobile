package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.movies.databinding.MovieCardBinding
import com.example.movies.databinding.MovieItemBinding

class MovieViewHolder(private val binding: MovieCardBinding, val onItemClick: (MovieData) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: MovieData? = null

    init {
        binding.root.setOnClickListener {
            currentMovie?.let {
                onItemClick(it)
            }
        }
    }

    fun bindingMovie(movie: MovieData) {
        currentMovie = movie
        binding.movieTitle.text = movie.name
        binding.movieRating.text = movie.rating.toString()
        binding.movieDescription.text = movie.annotation

        Glide.with(itemView.context)
            .load(movie.previewURL)
            .into(binding.movieImage)
    }
}
