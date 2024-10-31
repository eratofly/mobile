package com.example.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieItemBinding


class MovieAdapter(
    private val onItemClick: (MovieData) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {
    var movieList = listOf<MovieData>()

    override fun getItemCount(): Int = movieList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)

        return MovieViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val itemBinding = MovieItemBinding.bind(holder.itemView)
        val movie = movieList[position]

        itemBinding.name.text = movie.name
        itemBinding.description.text = movie.annotation
        itemBinding.ratingText.text = movie.rating.toString()
        val imageUrl = movie.previewURL

        println("NEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEN")
        println(imageUrl)

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(itemBinding.photo)

        itemBinding.movieItem.setOnClickListener {
            onItemClick(movie)
        }
    }
}
