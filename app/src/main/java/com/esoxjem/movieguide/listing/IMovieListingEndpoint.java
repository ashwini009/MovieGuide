package com.esoxjem.movieguide.listing;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author Ashwini Kumar.
 */

public interface IMovieListingEndpoint
{

    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    Observable<MovieViewModel> fetchPopularMovies();

    @GET("/3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    Observable<MovieViewModel> fetchHighestRatedMovies();
}
