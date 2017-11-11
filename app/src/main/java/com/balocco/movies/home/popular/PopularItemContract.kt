package com.balocco.movies.home.popular

import com.balocco.movies.data.model.Movie
import com.balocco.movies.mvp.BasePresenter
import com.balocco.movies.mvp.BaseView

interface PopularItemContract {

    interface Presenter : BasePresenter<View> {

        fun onMovieUpdated(movie: Movie)

    }

    interface View : BaseView {

        fun showTitle(title: String)

    }

}