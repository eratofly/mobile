package com.example.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieCardBinding
import com.example.movies.databinding.MovieItemBinding


class MovieAdapter(
    private val onItemClick: (MovieData) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {
    var MovieList = listOf<MovieData>()

    override fun getItemCount(): Int = MovieList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieCardBinding.inflate(inflater, parent, false)

        return MovieViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindingMovie(MovieList[position])
    }

}
