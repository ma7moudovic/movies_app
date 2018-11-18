package com.shar2wy.moviesapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.shar2wy.moviesapp.R
import com.shar2wy.moviesapp.adapters.MoviesAdapter
import com.shar2wy.moviesapp.models.moviesRepo.Movie
import com.shar2wy.moviesapp.models.moviesRepo.MovieRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var moviesAdapter: MoviesAdapter? = null
    private var mMovies: MutableList<Movie> = ArrayList()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
    }

    private var movieClicked: ((movie: Movie) -> Unit) = {
        startActivity(Intent(this, MovieDetailsActivity::class.java).putExtra(MovieDetailsActivity.DETAILED_MOVIE, it.id))
    }

    private fun initViews() {

        recycler_view_movies.setHasFixedSize(true)
        recycler_view_movies.itemAnimator = DefaultItemAnimator()

        moviesAdapter = MoviesAdapter(mMovies, movieClicked)

        recycler_view_movies.layoutManager = GridLayoutManager(this, 2)
        recycler_view_movies.adapter = moviesAdapter

        fetchMoviesList()
    }


    private fun fetchMoviesList() {

        val disposable = MovieRepo.getInstance(this).movies
                .subscribeOn(Schedulers.io())
//                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    mMovies.clear()
                    mMovies.addAll(movies)
                    moviesAdapter?.notifyDataSetChanged()
                }, { throwable -> Toast.makeText(this@MainActivity, throwable.message, Toast.LENGTH_SHORT).show() })

        compositeDisposable.add(disposable)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
