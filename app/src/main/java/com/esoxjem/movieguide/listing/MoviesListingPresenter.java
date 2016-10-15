package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.sorting.SortType;
import com.esoxjem.movieguide.sorting.SortingOptionStore;
import com.esoxjem.movieguide.util.RxUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author arun
 */
public class MoviesListingPresenter implements IMoviesListingPresenter
{
    private IMoviesListingView view;
    private Subscription popularMoviesSubscription;
    private Subscription highestRatedMoviesSubscription;
    private IMovieListingEndpoint movieListingEndpoint;
    private SortingOptionStore sortingOptionStore;
    private IFavoritesInteractor favoritesInteractor;

    public MoviesListingPresenter(IMovieListingEndpoint movieListingEndpoint, SortingOptionStore sortingOptionStore, IFavoritesInteractor favoritesInteractor)
    {
        this.movieListingEndpoint = movieListingEndpoint;
        this.sortingOptionStore = sortingOptionStore;
        this.favoritesInteractor = favoritesInteractor;
    }

    @Override
    public void setView(IMoviesListingView view)
    {
        this.view = view;
    }

    @Override
    public void destroy()
    {
        view = null;
        RxUtils.unsubscribe(popularMoviesSubscription, highestRatedMoviesSubscription);
    }

    @Override
    public void displayMovies()
    {
        int selectedOption = sortingOptionStore.getSelectedOption();
        if (selectedOption == SortType.MOST_POPULAR.getValue())
        {
            loadPopularMovies();
        } else if (selectedOption == SortType.HIGHEST_RATED.getValue())
        {
            loadHighestRatedMovies();
        } else
        {
            loadFavouriteMovies();
        }
    }

    private void loadPopularMovies()
    {
        popularMoviesSubscription = movieListingEndpoint.fetchPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    if (isViewAttached())
                    {
                        view.loadingStarted();
                    }
                })
                .subscribe(new Subscriber<MovieViewModel>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        view.loadingFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieViewModel movieViewModel)
                    {
                        view.showMovies(movieViewModel.getMovies());
                    }
                });
    }

    private void loadHighestRatedMovies()
    {
        highestRatedMoviesSubscription = movieListingEndpoint.fetchHighestRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                    if (isViewAttached())
                    {
                        view.loadingStarted();
                    }
                })
                .subscribe(new Subscriber<MovieViewModel>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        view.loadingFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(MovieViewModel movieViewModel)
                    {
                        view.showMovies(movieViewModel.getMovies());
                    }
                });
    }

    private void loadFavouriteMovies()
    {
        view.showMovies(favoritesInteractor.getFavorites());
    }

    private boolean isViewAttached()
    {
        return view != null;
    }
}
