package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.listing.Movie;

/**
 * @author arun
 */
public interface IMovieDetailsPresenter
{
    void showDetails(Movie movie);

    void showTrailers(Movie movie);

    void showReviews(Movie movie);

    void showFavoriteButton(Movie movie);

    void onFavoriteClick(Movie movie);

    void setView(IMovieDetailsView view);

    void destroy();
}
