package com.balocco.movies.home.popular.presentation

import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularItemContract
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

class PopularItemPresenter @Inject constructor(
) : ReactivePresenter(), PopularItemContract.Presenter {

    private lateinit var view: PopularItemContract.View

    override fun setView(view: PopularItemContract.View) {
        this.view = view
    }

    override fun onMovieUpdated(movie: Movie) {
        view.showTitle(movie.title)
    }

}