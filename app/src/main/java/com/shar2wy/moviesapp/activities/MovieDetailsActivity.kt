package com.shar2wy.moviesapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.shar2wy.moviesapp.R
import com.shar2wy.moviesapp.adapters.ReviewAdapter
import com.shar2wy.moviesapp.adapters.TrailerAdapter
import com.shar2wy.moviesapp.models.moviesRepo.Movie
import com.shar2wy.moviesapp.models.reviewRepo.Review
import com.shar2wy.moviesapp.models.reviewRepo.ReviewRepo
import com.shar2wy.moviesapp.models.trailerRepo.Trailer
import com.shar2wy.moviesapp.models.trailerRepo.TrailerRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {

    private var mMovie: Movie? = null
    private var mTrailerAdapter: TrailerAdapter? = null
    private var mReviewAdapter: RecyclerView.Adapter<*>? = null
    private val compositeDisposable = CompositeDisposable()
    private val mTrailers = ArrayList<Trailer>()
    private val mReviews = ArrayList<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    var onTrailerClicked: ((trailer: Trailer) -> Unit) = {
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

        mMovie?.let {

            val reviewsDisposable = ReviewRepo.getInstance(this).getReviews(it.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reviews ->
                        detail_reviews_cardview.visibility = if (reviews.isEmpty()) View.GONE else View.VISIBLE
                        mReviews.clear()
                        mReviews.addAll(reviews)
                        mReviewAdapter?.notifyDataSetChanged()
                    }, { throwable -> Toast.makeText(this@MovieDetailsActivity, throwable.message, Toast.LENGTH_SHORT).show() })


            val trailersDisposable = TrailerRepo.getInstance(this).getTrailers(it.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ trailers ->
                        detail_trailers_cardview.visibility = if (trailers.isEmpty()) View.GONE else View.VISIBLE
                        mTrailers.clear()
                        mTrailers.addAll(trailers)
                        mTrailerAdapter?.notifyDataSetChanged()
                    }, { throwable -> Toast.makeText(this@MovieDetailsActivity, throwable.message, Toast.LENGTH_SHORT).show() })


            compositeDisposable.add(reviewsDisposable)
            compositeDisposable.add(trailersDisposable)

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

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    companion object {

        val DETAILED_MOVIE = "detailed_movie"

        fun buildImageUrl(width: Int, fileName: String?): String {
            return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName
        }
    }
}
