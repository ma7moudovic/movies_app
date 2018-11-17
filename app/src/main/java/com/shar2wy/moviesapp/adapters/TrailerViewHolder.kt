package com.shar2wy.moviesapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.shar2wy.moviesapp.models.trailerRepo.Trailer
import kotlinx.android.synthetic.main.item_trailer.view.*

/**
 * Created by shar2wy
 * on 11/17/18.
 * Description: description goes here
 */

class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTrailer(trailer: Trailer) {
        itemView.trailer_name.text = trailer.name

        val yt_thumbnail_url = "http://img.youtube.com/vi/" + trailer.key + "/0.jpg"

        Glide
                .with(getContext())
                .load(yt_thumbnail_url)
                .into(itemView.trailer_image)

    }

    private fun getContext ():Context{
        return itemView.context
    }
}