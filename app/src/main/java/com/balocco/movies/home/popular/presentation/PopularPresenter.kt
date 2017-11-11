package com.balocco.movies.home.popular.presentation

import com.balocco.movies.common.scheduler.SchedulerProvider
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularContract
import com.balocco.movies.home.popular.usecase.FetchPopularMoviesUseCase
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

class PopularPresenter @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
) : ReactivePresenter(), PopularContract.Presenter {

    private var movies: MutableList<Movie> = arrayListOf<Movie>()
    private lateinit var view: PopularContract.View

    override fun setView(view: PopularContract.View) {
        this.view = view
    }

    override fun start() {
        if (movies.isEmpty()) {
            fetchMovies()
        }
    }

    private fun fetchMovies() {
        addDisposable(
                fetchPopularMoviesUseCase.execute()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .doOnSubscribe { view.showLoading() }
                        .subscribe({
                            movies.addAll(it.results)
                            view.showMovies(movies)
                            view.hideLoading()
                        }, {
                            view.hideLoading()
                        })
        )
    }

}