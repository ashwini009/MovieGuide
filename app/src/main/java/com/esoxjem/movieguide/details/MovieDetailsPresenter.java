package com.esoxjem.movieguide.details;

import android.support.annotation.NonNull;

import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.listing.Movie;
import com.esoxjem.movieguide.util.RxUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author arun
 */
public class MovieDetailsPresenter implements IMovieDetailsPresenter
{
    private IMovieDetailsView view;
    private IFavoritesInteractor favoritesInteractor;
    private Subscription trailersSubscription;
    private Subscription reviewSubscription;
    private IMovieDetailsEndpoint movieDetailsEndpoint;

    public MovieDetailsPresenter(IMovieDetailsEndpoint movieDetailsEndpoint, IFavoritesInteractor favoritesInteractor)
    {
        this.movieDetailsEndpoint = movieDetailsEndpoint;
        this.favoritesInteractor = favoritesInteractor;
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
        RxUtils.unsubscribe(trailersSubscription, reviewSubscription);
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
        trailersSubscription = movieDetailsEndpoint.getMovieTrailers(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VideoViewModel>()
                {
                    @Override
                    public void onCompleted()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }

                    @Override
                    public void onNext(@NonNull VideoViewModel videoViewModel)
                    {
                        if (isViewAttached())
                        {
                            view.showTrailers(videoViewModel.getVideos());
                        }
                    }
                });
    }

    @Override
    public void showReviews(Movie movie)
    {
        reviewSubscription = movieDetailsEndpoint.getMovieReviews(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReviewViewModel>()
                {
                    @Override
                    public void onCompleted()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }

                    @Override
                    public void onNext(@NonNull ReviewViewModel reviewViewModel)
                    {
                        if (isViewAttached())
                        {
                            view.showReviews(reviewViewModel.getReviews());
                        }
                    }
                });
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
