package com.balocco.movies.home.popular

import com.balocco.movies.data.model.Movie
import com.balocco.movies.mvp.BasePresenter
import com.balocco.movies.mvp.BaseView

interface PopularContract {

    interface Presenter : BasePresenter<View> {

        fun start()

    }

    interface View : BaseView {

        fun showLoading()

        fun hideLoading()

        fun showMovies(movies: List<Movie>)

    }

}