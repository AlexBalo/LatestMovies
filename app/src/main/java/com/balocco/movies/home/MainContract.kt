package com.balocco.movies.home

import android.os.Bundle
import com.balocco.movies.data.model.Movie
import com.balocco.movies.mvp.BasePresenter
import com.balocco.movies.mvp.BaseView

interface MainContract {

    interface Presenter : BasePresenter<View> {

        fun start(savedInstanceState: Bundle?)

        fun onMovieSelected(movie: Movie)

    }

    interface View : BaseView {

        fun showPopular()

        fun showMovieDetail(movie: Movie)

    }

}