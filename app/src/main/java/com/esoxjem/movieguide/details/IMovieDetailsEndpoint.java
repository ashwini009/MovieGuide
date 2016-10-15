package com.esoxjem.movieguide.details;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Ashwini Kumar.
 */

public interface IMovieDetailsEndpoint
{

    @GET("/3/movie/{id}/videos")
    Observable<VideoViewModel> getMovieTrailers(@Path("id") String id);

    @GET("/3/movie/{id}/reviews")
    Observable<ReviewViewModel> getMovieReviews(@Path("id") String id);
}
