package com.shar2wy.moviesapp.activities;

import static com.shar2wy.moviesapp.activities.MovieDetailsActivity.DETAILED_MOVIE;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.adapters.MoviesAdapter;
import com.shar2wy.moviesapp.models.moviesRepo.Movie;
import com.shar2wy.moviesapp.models.moviesRepo.MovieRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieClickListener {

    @BindView(R.id.recycler_view_movies)
    RecyclerView recyclerViewMovies;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    MoviesAdapter moviesAdapter;
    List<Movie> mMovies = new ArrayList<>();
    GridLayoutManager layoutManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {

        recyclerViewMovies.setHasFixedSize(true);
        recyclerViewMovies.setItemAnimator(new DefaultItemAnimator());

        moviesAdapter = new MoviesAdapter(this, mMovies);
        moviesAdapter.setOnMovieClickListener(this);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerViewMovies.setLayoutManager(layoutManager);
        recyclerViewMovies.setAdapter(moviesAdapter);

        fetchMoviesList();
    }


    private void fetchMoviesList() {

        Disposable disposable = MovieRepo.getInstance(this).getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        mMovies.clear();
                        mMovies.addAll(movies);
                        moviesAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        compositeDisposable.add(disposable);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClick(@NonNull Movie movie) {
        startActivity(new Intent(this, MovieDetailsActivity.class).putExtra(DETAILED_MOVIE, movie.getId()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
