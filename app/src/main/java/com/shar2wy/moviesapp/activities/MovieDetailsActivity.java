package com.shar2wy.moviesapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.adapters.ReviewAdapter;
import com.shar2wy.moviesapp.adapters.TrailerAdapter;
import com.shar2wy.moviesapp.models.moviesRepo.Movie;
import com.shar2wy.moviesapp.models.reviewRepo.Review;
import com.shar2wy.moviesapp.models.reviewRepo.ReviewRepo;
import com.shar2wy.moviesapp.models.trailerRepo.Trailer;
import com.shar2wy.moviesapp.models.trailerRepo.TrailerRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.OnTrailerClickListener {

    public static final String DETAILED_MOVIE = "detailed_movie";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Movie mMovie;
    @BindView(R.id.detailed_image)
    ImageView mImageView;
    @BindView(R.id.detailed_title)
    TextView mTitleView;
    @BindView(R.id.detailed_overview)
    TextView mOverviewView;
    @BindView(R.id.detailed_date)
    TextView mDateView;
    @BindView(R.id.detailed_vote_average)
    TextView mVoteAverageView;
    @BindView(R.id.detailed_trailers)
    RecyclerView mTrailersView;
    @BindView(R.id.detailed_reviews)
    RecyclerView mReviewsView;
    @BindView(R.id.detail_reviews_cardview)
    CardView mReviewsCardView;
    @BindView(R.id.detail_trailers_cardview)
    CardView mTrailersCardView;
    TrailerAdapter mTrailerAdapter;
    RecyclerView.Adapter mReviewAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Trailer> mTrailers = new ArrayList<>();
    private List<Review> mReviews = new ArrayList<>();

    public static String buildImageUrl(int width, String fileName) {
        return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    private void initViews() {

        mTrailersView.setHasFixedSize(true);
        mTrailersView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mReviewsView.setHasFixedSize(true);
        mReviewsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mTrailerAdapter = new TrailerAdapter(this, mTrailers);
        mTrailerAdapter.setOnTrailerClickListener(this);

        mTrailersView.setAdapter(mTrailerAdapter);

        mReviewAdapter = new ReviewAdapter(this, mReviews);
        mReviewsView.setAdapter(mReviewAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mMovie = Realm.getDefaultInstance().where(Movie.class).equalTo("id", extras.getInt(DETAILED_MOVIE)).findFirst();
        }

        if (mMovie != null) {

            Disposable reviewsDisposable = ReviewRepo.getInstance(this).getReviews(mMovie.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(reviews -> {
                        mReviewsCardView.setVisibility(reviews.isEmpty() ? View.GONE : View.VISIBLE);
                        mReviews.clear();
                        mReviews.addAll(reviews);
                        mReviewAdapter.notifyDataSetChanged();
                    }, throwable -> Toast.makeText(MovieDetailsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());


            Disposable trailersDisposable = TrailerRepo.getInstance(this).getTrailers(mMovie.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(trailers -> {
                        mTrailersCardView.setVisibility(trailers.isEmpty() ? View.GONE : View.VISIBLE);
                        mTrailers.clear();
                        mTrailers.addAll(trailers);
                        mTrailerAdapter.notifyDataSetChanged();
                    }, throwable -> Toast.makeText(MovieDetailsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());


            compositeDisposable.add(reviewsDisposable);
            compositeDisposable.add(trailersDisposable);

            Glide
                    .with(this)
                    .load(buildImageUrl(342, mMovie.getBackdropPath()))
                    .into(mImageView);

            mTitleView.setText(mMovie.getTitle());
            mOverviewView.setText(mMovie.getOverview());

            mDateView.setText(mMovie.getReleaseDate());

            mVoteAverageView.setText(mMovie.getVoteAverage() + "/10");

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void OnTrailerClick(@NonNull Trailer trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
        startActivity(intent);
    }
}
