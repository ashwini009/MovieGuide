package com.esoxjem.movieguide.listing;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.esoxjem.movieguide.Api;
import com.esoxjem.movieguide.R;

import java.util.List;

/**
 * @author arun
 */
public class MoviesListingAdapter extends RecyclerView.Adapter<MovieListingHolder>
{
    private Callback callback;
    private List<Movie> movies;
    private Context context;
    MovieListingViewHolderFactory viewHolderFactory;

    public MoviesListingAdapter(Callback callback, MovieListingViewHolderFactory viewHolderFactory)
    {
        this.callback = callback;
        this.viewHolderFactory = viewHolderFactory;
    }

    @Override
    public MovieListingHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        return viewHolderFactory.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final MovieListingHolder holder, int position)
    {
        final Movie movie = movies.get(position);
        holder.itemView.setOnClickListener(view -> callback.onMovieClicked(movie));

        holder.name.setText(movie.getTitle());
        Glide.with(context).load(Api.POSTER_PATH + movie.getPosterPath())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new BitmapImageViewTarget(holder.poster)
                {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim)
                    {
                        super.onResourceReady(bitmap, anim);

                        Palette.from(bitmap).generate(palette -> holder.titleBackground.setBackgroundColor(palette.getVibrantColor(context
                                .getResources().getColor(R.color.black_translucent_60))));
                    }
                });
    }

    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    public void updateMovieList(List<Movie> movies)
    {
        this.movies = movies;
    }

    public interface Callback
    {
        void onMovieClicked(Movie movie);
    }
}
