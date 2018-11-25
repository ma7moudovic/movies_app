package com.shar2wy.moviesapp.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.shar2wy.moviesapp.R
import com.shar2wy.moviesapp.adapters.ReviewAdapter
import com.shar2wy.moviesapp.adapters.TrailerAdapter
import com.shar2wy.moviesapp.models.moviesRepo.Movie
import com.shar2wy.moviesapp.models.reviewRepo.Review
import com.shar2wy.moviesapp.models.trailerRepo.Trailer
import com.shar2wy.moviesapp.viewModels.MoviesViewModel
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {

    private var mMovie: Movie? = null
    private var mTrailerAdapter: TrailerAdapter? = null
    private var mReviewAdapter: RecyclerView.Adapter<*>? = null
    private val mTrailers = ArrayList<Trailer>()
    private val mReviews = ArrayList<Review>()
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    private var onTrailerClicked: ((trailer: Trailer) -> Unit) = {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://www.youtube.com/watch?v=" + it.key)
        startActivity(intent)
    }


    private fun initViews() {

        detailed_trailers.setHasFixedSize(true)
        detailed_trailers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        detailed_reviews.setHasFixedSize(true)
        detailed_reviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mTrailerAdapter = TrailerAdapter(mTrailers, onTrailerClicked)

        detailed_trailers.adapter = mTrailerAdapter

        mReviewAdapter = ReviewAdapter(mReviews)
        detailed_reviews.adapter = mReviewAdapter

        val extras = intent.extras
        if (extras != null) mMovie = Realm.getDefaultInstance().where(Movie::class.java).equalTo("id", extras.getInt(DETAILED_MOVIE)).findFirst()

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        moviesViewModel.reviews.observe(this, android.arch.lifecycle.Observer { reviews ->
            reviews?.let {
                detail_reviews_cardview.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                mReviews.clear()
                mReviews.addAll(it)
                mReviewAdapter?.notifyDataSetChanged()
            }

        })

        moviesViewModel.trailers.observe(this, android.arch.lifecycle.Observer { trailers ->
            trailers?.let {
                detail_trailers_cardview.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                mTrailers.clear()
                mTrailers.addAll(it)
                mTrailerAdapter?.notifyDataSetChanged()
            }
        })
        mMovie?.let {

            moviesViewModel.getReviews(it.id)
            moviesViewModel.getTrailers(it.id)

            Glide
                    .with(this)
                    .load(buildImageUrl(342, it.backdropPath))
                    .into(detailed_image)

            detailed_title.text = it.title
            detailed_overview.text = it.overview
            detailed_date.text = it.releaseDate
            detailed_vote_average.text = it.voteAverage?.toString() + "/10"

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val DETAILED_MOVIE = "detailed_movie"

        fun buildImageUrl(width: Int, fileName: String?): String {
            return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName
        }
    }
}
