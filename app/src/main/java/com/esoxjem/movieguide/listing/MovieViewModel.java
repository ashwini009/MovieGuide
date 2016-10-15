package com.esoxjem.movieguide.listing;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * @author Ashwini Kumar.
 */

public class MovieViewModel
{
    @Json(name = "page")
    private int pageNumber;

    @Json(name = "results")
    private List<Movie> movies;

    public int getPageNumber()
    {
        return pageNumber;
    }

    public List<Movie> getMovies()
    {
        return movies;
    }
}
