package com.shar2wy.moviesapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shar2wy.moviesapp.R
import com.shar2wy.moviesapp.models.moviesRepo.Movie

/**
 * Created by shar2wy on 4/7/17.
 */

class MoviesAdapter(private val mMovies: List<Movie>, var movieClicked: ((movie: Movie) -> Unit)) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, movieClicked)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = mMovies[position]
        holder.bindMovie(movie)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }
}
