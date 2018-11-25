package com.shar2wy.moviesapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.shar2wy.moviesapp.R
import com.shar2wy.moviesapp.adapters.MoviesAdapter
import com.shar2wy.moviesapp.models.moviesRepo.Movie
import com.shar2wy.moviesapp.viewModels.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var moviesAdapter: MoviesAdapter? = null
    private var mMovies: MutableList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
    }

    private var movieClicked: ((movie: Movie) -> Unit) = {
        startActivity(Intent(this, MovieDetailsActivity::class.java).putExtra(MovieDetailsActivity.DETAILED_MOVIE, it.id))
    }

    private var moviesListViewModel: MoviesViewModel? = null

    private fun initViews() {

        recycler_view_movies.setHasFixedSize(true)
        recycler_view_movies.itemAnimator = DefaultItemAnimator()

        moviesAdapter = MoviesAdapter(mMovies, movieClicked)

        recycler_view_movies.layoutManager = GridLayoutManager(this, 2)
        recycler_view_movies.adapter = moviesAdapter

        moviesListViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        moviesListViewModel?.getMovies()

        moviesListViewModel?.movies?.observe(this, Observer { movies ->
            movies?.let {
                mMovies.clear()
                mMovies.addAll(it)
                moviesAdapter?.notifyDataSetChanged()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
