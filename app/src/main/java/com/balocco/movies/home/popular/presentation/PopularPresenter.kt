package com.balocco.movies.home.popular.presentation

import android.os.Bundle
import android.support.annotation.StringRes
import com.balocco.movies.R
import com.balocco.movies.common.scheduler.SchedulerProvider
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularContract
import com.balocco.movies.home.popular.data.MoviePopularityDescendingComparator
import com.balocco.movies.home.popular.data.MovieReleaseDateAscendingComparator
import com.balocco.movies.home.popular.data.MovieReleaseDateDescendingComparator
import com.balocco.movies.home.popular.usecase.FetchPopularMoviesUseCase
import com.balocco.movies.mvp.ReactivePresenter
import java.util.*
import javax.inject.Inject

private const val INVALID = -1
private const val KEY_CURRENT_PAGE = "KEY_CURRENT_PAGE"
private const val KEY_TOTAL_PAGES = "KEY_TOTAL_PAGES"
private const val KEY_MOVIES = "KEY_MOVIES"
private const val KEY_FILTER = "KEY_FILTER"

class PopularPresenter @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
) : ReactivePresenter(), PopularContract.Presenter {

    private enum class Filter {
        POPULARITY,
        TIME_ASC,
        TIME_DESC
    }

    private var currentPage = INVALID
    private var totalPages = INVALID
    private var filter = Filter.POPULARITY
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
            filter = Filter.values()[savedInstanceState.getInt(KEY_FILTER)]
        }

        when (filter) {
            Filter.TIME_DESC -> {
                applyReleaseDateDescendingFilter()
            }
            Filter.TIME_ASC -> {
                applyReleaseDateAscendingFilter()
            }
            else -> {
                if (movies.isEmpty()) {
                    fetchMovies()
                } else {
                    view.hideLoading()
                    view.showMovies(movies)
                }
            }
        }
    }

    override fun onSaveInstanceState(outBundle: Bundle) {
        outBundle.putInt(KEY_CURRENT_PAGE, currentPage)
        outBundle.putInt(KEY_TOTAL_PAGES, totalPages)
        outBundle.putParcelableArrayList(KEY_MOVIES, movies)
        outBundle.putInt(KEY_FILTER, filter.ordinal)
    }

    override fun stop() {
        view.hideFilterMessage()
    }

    override fun onMovieSelected(movie: Movie) {
        view.hideFilterMessage()
        view.navigateToDetail(movie)
    }

    override fun onLoadMore() {
        fetchMovies()
    }

    override fun onSortByTimeDescending() {
        if (filter == Filter.TIME_DESC) {
            return
        }
        applyReleaseDateDescendingFilter()
    }

    override fun onSortByTimeAscending() {
        if (filter == Filter.TIME_ASC) {
            return
        }
        applyReleaseDateAscendingFilter()
    }

    override fun onFilterCleared() {
        filter = Filter.POPULARITY
        view.hideLoading()
        view.enabledLoadingCallbacks()
        Collections.sort(movies, MoviePopularityDescendingComparator())
        view.showMovies(movies)
    }

    private fun fetchMovies() {
        if (filter != Filter.POPULARITY) {
            return
        }

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

    private fun applyReleaseDateDescendingFilter() {
        applySortingFilter(Filter.TIME_DESC, MovieReleaseDateDescendingComparator(), R.string.popular_filter_message_desc)
    }

    private fun applyReleaseDateAscendingFilter() {
        applySortingFilter(Filter.TIME_ASC, MovieReleaseDateAscendingComparator(), R.string.popular_filter_message_asc)
    }

    private fun applySortingFilter(newFilter: Filter,
                                   comparator: Comparator<Movie>,
                                   @StringRes messageRes: Int) {
        filter = newFilter
        view.hideLoading()
        view.disableLoadingCallbacks()
        Collections.sort(movies, comparator)
        view.showMovies(movies)
        view.showFilterEnabledMessage(messageRes)
        view.scrollListToTop()
    }

}