package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.listing.Movie;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author arun
 */
public class MovieDetailsPresenter implements IMovieDetailsPresenter
{
    private IMovieDetailsView view;
    private IFavoritesInteractor favoritesInteractor;
    private IMovieDetailsEndpoint movieDetailsEndpoint;
    private CompositeDisposable compositeDisposable;

    public MovieDetailsPresenter(IMovieDetailsEndpoint movieDetailsEndpoint, IFavoritesInteractor favoritesInteractor)
    {
        this.movieDetailsEndpoint = movieDetailsEndpoint;
        this.favoritesInteractor = favoritesInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(IMovieDetailsView view)
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
    public void showDetails(Movie movie)
    {
        if (isViewAttached())
        {
            view.showDetails(movie);
        }
    }

    private boolean isViewAttached()
    {
        return view != null;
    }

    @Override
    public void showTrailers(Movie movie)
    {
        compositeDisposable.add(movieDetailsEndpoint.getMovieTrailers(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTrailersResult, throwable -> onError()));
    }

    private void onError()
    {
        // do nothing
    }

    private void onTrailersResult(VideoViewModel videoViewModel)
    {
        if (isViewAttached())
        {
            view.showTrailers(videoViewModel.getVideos());
        } else
        {
            // do nothing
        }
    }

    @Override
    public void showReviews(Movie movie)
    {
        compositeDisposable.add(movieDetailsEndpoint.getMovieReviews(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReviewsResult, throwable -> onError()));

    }

    private void onReviewsResult(ReviewViewModel reviewViewModel)
    {

        if (isViewAttached())
        {
            view.showReviews(reviewViewModel.getReviews());
        } else
        {
            // do nothing
        }
    }

    @Override
    public void showFavoriteButton(Movie movie)
    {
        boolean isFavorite = favoritesInteractor.isFavorite(movie.getId());
        if (isViewAttached())
        {
            if (isFavorite)
            {
                view.showFavorited();
            } else
            {
                view.showUnFavorited();
            }
        }
    }

    @Override
    public void onFavoriteClick(Movie movie)
    {
        if (isViewAttached())
        {
            boolean isFavorite = favoritesInteractor.isFavorite(movie.getId());
            if (isFavorite)
            {
                favoritesInteractor.unFavorite(movie.getId());
                view.showUnFavorited();
            } else
            {
                favoritesInteractor.setFavorite(movie);
                view.showFavorited();
            }
        }
    }
}
