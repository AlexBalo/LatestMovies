package com.balocco.movies.home.presentation

import android.os.Bundle
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.MainContract
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
) : ReactivePresenter(), MainContract.Presenter {

    private lateinit var view: MainContract.View

    override fun setView(view: MainContract.View) {
        this.view = view
    }

    override fun start(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            view.showPopular()
            return
        }
    }

    override fun onMovieSelected(movie: Movie) {
        view.showMovieDetail(movie)
    }

}