package com.balocco.movies.home.detail.presentation

import com.balocco.movies.common.UrlProvider
import com.balocco.movies.common.scheduler.SchedulerProvider
import com.balocco.movies.common.usecase.FetchGenresUseCase
import com.balocco.movies.common.usecase.ScreenSizeUseCase
import com.balocco.movies.data.model.Movie
import com.balocco.movies.data.store.GenreStore
import com.balocco.movies.home.detail.DetailContract
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

private const val EMPTY_GENRES = "-"

class DetailPresenter @Inject constructor(
        private val urlProvider: UrlProvider,
        private val genreStore: GenreStore,
        private val schedulerProvider: SchedulerProvider,
        private val screenSizeUseCase: ScreenSizeUseCase,
        private val dateToHumanReadableUseCase: DateToHumanReadableUseCase,
        private val fetchGenresUseCase: FetchGenresUseCase
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

        val width = screenSizeUseCase.screenWidth()
        val height = width * 9 / 16
        view.setBackdropSizes(width, height)

        fetchGenres(movie)

        val backdropUrl = urlProvider.provideUrlForBackdrop(movie.backdropPath)
        val readableDate = dateToHumanReadableUseCase.execute(movie.releaseDate)
        with(view) {
            showBackdrop(backdropUrl)
            showTitle(movie.title)
            showDescription(movie.overview)
            showReleaseDate(readableDate)
            showOriginalLanguage(movie.originalLanguage)
            showRating(movie.voteAverage.toString())
        }
    }

    private fun fetchGenres(movie: Movie) {
        if (genreStore.hasData()) {
            showGenres(movie)
            return
        }

        addDisposable(
                fetchGenresUseCase.execute()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            showGenres(movie)
                        }, {
                            view.showGenres(EMPTY_GENRES)
                        })
        )
    }

    private fun showGenres(movie: Movie) {
        val genres = genreStore.genresFromIds(movie.genres)
        var genresText = ""
        for (i in 0 until genres.size) {
            if (i > 0) {
                genresText += ", "
            }
            genresText += genres[i].name
        }
        view.showGenres(genresText)
    }

}