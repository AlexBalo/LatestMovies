package com.balocco.movies.home.popular.presentation

import android.os.Bundle
import com.balocco.movies.R
import com.balocco.movies.common.scheduler.SchedulerProvider
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularContract
import com.balocco.movies.home.popular.usecase.FetchPopularMoviesUseCase
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

private const val INVALID = -1
private const val KEY_CURRENT_PAGE = "KEY_CURRENT_PAGE"
private const val KEY_TOTAL_PAGES = "KEY_TOTAL_PAGES"
private const val KEY_MOVIES = "KEY_MOVIES"

class PopularPresenter @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
) : ReactivePresenter(), PopularContract.Presenter {

    private var currentPage: Int = INVALID
    private var totalPages: Int = INVALID
    private var movies: ArrayList<Movie> = arrayListOf<Movie>()

    private lateinit var view: PopularContract.View

    override fun setView(view: PopularContract.View) {
        this.view = view
    }

    override fun start(savedInstanceState: Bundle?) {
        view.setTitle(R.string.title_popular_feed)

        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(KEY_CURRENT_PAGE)
            totalPages = savedInstanceState.getInt(KEY_TOTAL_PAGES)
            movies = savedInstanceState.getParcelableArrayList(KEY_MOVIES)
        }

        if (movies.isEmpty()) {
            fetchMovies()
        } else {
            view.hideLoading()
            view.showMovies(movies)
        }
    }

    override fun onSaveInstanceState(outBundle: Bundle) {
        outBundle.putInt(KEY_CURRENT_PAGE, currentPage)
        outBundle.putInt(KEY_TOTAL_PAGES, totalPages)
        outBundle.putParcelableArrayList(KEY_MOVIES, movies)
    }

    override fun onMovieSelected(movie: Movie) {
        view.navigateToDetail(movie)
    }

    override fun onLoadMore() {
        fetchMovies()
    }

    private fun fetchMovies() {
        val pageToFetch = if (currentPage == INVALID) 1 else currentPage + 1
        if (pageToFetch == totalPages) {
            return
        }

        addDisposable(
                fetchPopularMoviesUseCase.execute(pageToFetch)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .doOnSubscribe {
                            if (movies.isEmpty()) {
                                view.showLoading()
                            }
                        }
                        .subscribe({
                            currentPage = it.page
                            totalPages = it.totalPages
                            movies.addAll(it.results)
                            view.showMovies(movies)
                            view.hideLoading()
                        }, {
                            view.hideLoading()
                        })
        )
    }

}