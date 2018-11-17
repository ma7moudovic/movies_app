package com.shar2wy.moviesapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shar2wy.moviesapp.R
import com.shar2wy.moviesapp.models.trailerRepo.Trailer

/**
 * Created by shar2wy on 4/8/17.
 */

class TrailerAdapter(internal var trailers: List<Trailer>, var trailerClicked: ((trailer: Trailer) -> Unit)) : RecyclerView.Adapter<TrailerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        return TrailerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = trailers[position]
        holder.bindTrailer(trailer)
        holder.itemView.setOnClickListener { trailerClicked(trailer) }
    }

    override fun getItemCount(): Int {
        return trailers.size
    }
}
