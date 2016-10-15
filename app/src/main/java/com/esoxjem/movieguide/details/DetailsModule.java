package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.ActivityScope;
import com.esoxjem.movieguide.favorites.IFavoritesInteractor;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author pulkitkumar
 * @author Ashwini Kumar.
 */
@Module
public class DetailsModule
{
    private MovieDetailsFragment movieDetailsFragment;

    public DetailsModule(MovieDetailsFragment movieDetailsFragment)
    {
        this.movieDetailsFragment = movieDetailsFragment;
    }

    @Provides
    @ActivityScope
    MovieDetailsFragment provideMovieDetailsFragment()
    {
        return movieDetailsFragment;
    }


    @Provides
    @ActivityScope
    IMovieDetailsPresenter providePresenter(IMovieDetailsEndpoint movieDetailsEndpoint,
                                            IFavoritesInteractor favoritesInteractor)
    {
        return new MovieDetailsPresenter(movieDetailsEndpoint, favoritesInteractor);
    }

    @Provides
    @ActivityScope
    IMovieDetailsEndpoint provideMovieDetailsEndpoint(Retrofit retrofit)
    {
        return retrofit.create(IMovieDetailsEndpoint.class);
    }
}
