package com.esoxjem.movieguide.listing;

import java.util.List;

import rx.Observable;

/**
 * @author arun
 */
public interface IMoviesListingInteractor
{
    Observable<List<Movie>> fetchMovies();
}
