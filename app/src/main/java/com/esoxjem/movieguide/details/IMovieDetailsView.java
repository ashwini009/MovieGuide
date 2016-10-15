package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.listing.Movie;

import java.util.List;

/**
 * @author arun
 */
public interface IMovieDetailsView
{
    void showDetails(Movie movie);

    void showTrailers(List<Video> trailers);

    void showReviews(List<Review> reviews);

    void showFavorited();

    void showUnFavorited();
}
