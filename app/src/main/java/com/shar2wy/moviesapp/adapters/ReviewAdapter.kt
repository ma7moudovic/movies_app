package com.shar2wy.moviesapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shar2wy.moviesapp.R
import com.shar2wy.moviesapp.models.reviewRepo.Review

/**
 * Created by shar2wy on 4/8/17.
 */

class ReviewAdapter(private val mReviews: List<Review>) : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = mReviews[position]
        holder.bindReview(review)
    }

    override fun getItemCount(): Int {
        return mReviews.size
    }
}
