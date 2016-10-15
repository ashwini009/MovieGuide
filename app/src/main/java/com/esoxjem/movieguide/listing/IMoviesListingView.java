package com.esoxjem.movieguide.listing;

import java.util.List;

/**
 * @author arun
 */
public interface IMoviesListingView
{
    void showMovies(List<Movie> movies);

    void loadingStarted();

    void loadingFailed(String errorMessage);

    void onMovieClicked(Movie movie);
}
