package com.shar2wy.moviesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shar2wy.moviesapp.R;
import com.shar2wy.moviesapp.models.trailerRepo.Trailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shar2wy on 4/8/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    Context mContext;
    List<Trailer> trailers;
    OnTrailerClickListener mOnTrailerClickListener;

    public TrailerAdapter(Context mContext, List<Trailer> trailers) {
        this.mContext = mContext;
        this.trailers = trailers;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        final Trailer trailer = trailers.get(position);

        String yt_thumbnail_url = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";

        holder.trailerTitle.setText(trailer.getName());

        Glide
                .with(mContext)
                .load(yt_thumbnail_url)
                .into(holder.trailerThumb);

        holder.itemView.setOnClickListener(view -> {
            if (mOnTrailerClickListener != null) {
                mOnTrailerClickListener.OnTrailerClick(trailer);
            }
        });
    }

    public void setOnTrailerClickListener(OnTrailerClickListener listener) {
        mOnTrailerClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public interface OnTrailerClickListener {
        void OnTrailerClick(@NonNull Trailer trailer);
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trailer_name)
        TextView trailerTitle;

        @BindView(R.id.trailer_image)
        ImageView trailerThumb;

        public TrailerViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
