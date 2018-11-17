package com.shar2wy.moviesapp.adapters

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import com.shar2wy.moviesapp.models.reviewRepo.Review
import kotlinx.android.synthetic.main.item_review.view.*

/**
 * Created by shar2wy
 * on 11/17/18.
 * Description: description goes here
 */

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindReview(review: Review) {
        itemView.review_author.text = review.author
        itemView.review_content.text = Html.fromHtml(review.content)
    }
}