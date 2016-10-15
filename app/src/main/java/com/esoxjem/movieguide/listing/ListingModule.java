package com.esoxjem.movieguide.listing;

import android.content.Context;

import com.esoxjem.movieguide.ActivityScope;
import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.sorting.SortingOptionStore;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author pulkitkumar
 * @author Ashwini Kumar.
 */
@Module
public class ListingModule
{
    private MoviesListingFragment moviesListingFragment;

    public ListingModule(MoviesListingFragment moviesListingFragment)
    {
        this.moviesListingFragment = moviesListingFragment;
    }

    @Provides
    @ActivityScope
    MoviesListingFragment provideMoviesListingFragment()
    {
        return moviesListingFragment;
    }

    @Provides
    @ActivityScope
    IMoviesListingPresenter provideMovieListingPresenter(IMovieListingEndpoint movieListingEndpoint, SortingOptionStore sortingOptionStore, IFavoritesInteractor favoritesInteractor)
    {
        return new MoviesListingPresenter(movieListingEndpoint, sortingOptionStore, favoritesInteractor);
    }

    @Provides
    @ActivityScope
    IMovieListingEndpoint provideMovieListingEndpoint(Retrofit retrofit)
    {
        return retrofit.create(IMovieListingEndpoint.class);
    }

    @Provides
    @ActivityScope
    SortingOptionStore providesSortingOptionStore(Context context)
    {
        return new SortingOptionStore(context);
    }

    @Provides
    @ActivityScope
    IMoviesListingView provideMoviesListingView()
    {
        return moviesListingFragment;
    }

    @Provides
    @ActivityScope
    MovieListingViewHolderFactory provideViewHolderFactory()
    {
        return new MovieListingHolderFactory();
    }

    @Provides
    @ActivityScope
    MoviesListingAdapter provideListingAdapter(MovieListingHolderFactory movieListingHolderFactory)
    {
        return new MoviesListingAdapter(moviesListingFragment, movieListingHolderFactory);
    }
}
