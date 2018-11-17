package com.shar2wy.moviesapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.shar2wy.moviesapp.models.moviesRepo.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by shar2wy
 * on 11/17/18.
 * Description: description goes here
 */

class MovieViewHolder(itemView: View, var movieClicked: ((movie: Movie) -> Unit)) : RecyclerView.ViewHolder(itemView) {

    fun bindMovie(movie: Movie){
        itemView.movie_item_title.text = movie.title
        val imageUrl = "http://image.tmdb.org/t/p/w185" + movie.posterPath

        Glide
                .with(getContext())
                .load(imageUrl)
                .into(itemView.movie_item_image)

        itemView.setOnClickListener {movieClicked(movie)}
    }

    private fun getContext():Context{
        return itemView.context
    }
}
