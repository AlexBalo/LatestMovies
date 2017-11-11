package com.balocco.movies.home.detail.presentation

import com.balocco.movies.common.scheduler.SchedulerProvider
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.detail.DetailContract
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

class DetailPresenter @Inject constructor(
        private val schedulerProvider: SchedulerProvider
) : ReactivePresenter(), DetailContract.Presenter {

    private lateinit var view: DetailContract.View

    override fun setView(view: DetailContract.View) {
        this.view = view
    }

    override fun start(movie: Movie?) {
        if (movie == null) {
            view.navigateBack()
            return
        }
        view.setTitle(movie.title)
    }

}