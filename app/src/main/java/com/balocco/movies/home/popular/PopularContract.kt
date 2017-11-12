package com.balocco.movies.home.popular

import android.os.Bundle
import android.support.annotation.StringRes
import com.balocco.movies.data.model.Movie
import com.balocco.movies.mvp.BasePresenter
import com.balocco.movies.mvp.BaseView

interface PopularContract {

    interface Presenter : BasePresenter<View> {

        fun start(savedInstanceState: Bundle?)

        fun onSaveInstanceState(outBundle: Bundle)

        fun stop()

        fun onMovieSelected(movie: Movie)

        fun onLoadMore()

        fun onSortReleaseDateDescendingClicked()

        fun onSortReleaseDateAscendingClicked()

        fun onFilterCleared()

    }

    interface View : BaseView {

        fun setTitle(titleRes: Int)

        fun showLoading()

        fun hideLoading()

        fun showMovies(movies: List<Movie>)

        fun navigateToDetail(movie: Movie)

        fun scrollListToTop()

        fun showFilterMessage(@StringRes messageRes: Int)

        fun hideFilterMessage()

        fun enableLoadingCallbacks()

        fun disableLoadingCallbacks()

    }

}