package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.sorting.SortType;
import com.esoxjem.movieguide.sorting.SortingOptionStore;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author arun
 */
public class MoviesListingPresenter implements IMoviesListingPresenter
{
    private IMoviesListingView view;
    private CompositeDisposable compositeDisposable;
    private IMovieListingEndpoint movieListingEndpoint;
    private SortingOptionStore sortingOptionStore;
    private IFavoritesInteractor favoritesInteractor;

    public MoviesListingPresenter(IMovieListingEndpoint movieListingEndpoint, SortingOptionStore sortingOptionStore, IFavoritesInteractor favoritesInteractor)
    {
        this.movieListingEndpoint = movieListingEndpoint;
        this.sortingOptionStore = sortingOptionStore;
        this.favoritesInteractor = favoritesInteractor;
        compositeDisposable = new CompositeDisposable();
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
        compositeDisposable.clear();
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
        compositeDisposable.add(movieListingEndpoint.fetchPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (isViewAttached())
                    {
                        view.loadingStarted();
                    }
                })
                .subscribeWith(new DisposableObserver<MovieViewModel>()
                {
                    @Override
                    public void onComplete()
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
                }));
    }

    private void loadHighestRatedMovies()
    {
        compositeDisposable.add(movieListingEndpoint.fetchHighestRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (isViewAttached())
                    {
                        view.loadingStarted();
                    }
                })
                .subscribeWith(new DisposableObserver<MovieViewModel>()
                {
                    @Override
                    public void onComplete()
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
                }));
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
