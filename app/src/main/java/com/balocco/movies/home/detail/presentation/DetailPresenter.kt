package com.balocco.movies.home.detail.presentation

import com.balocco.movies.common.UrlProvider
import com.balocco.movies.common.scheduler.SchedulerProvider
import com.balocco.movies.common.usecase.ScreenSizeUseCase
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.detail.DetailContract
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

class DetailPresenter @Inject constructor(
        private val urlProvider: UrlProvider,
        private val schedulerProvider: SchedulerProvider,
        private val screenSizeUseCase: ScreenSizeUseCase,
        private val dateToHumanReadableUseCase: DateToHumanReadableUseCase
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

        val backdropUrl = urlProvider.provideUrlForBackdrop(movie.backdropPath)
        view.showBackdrop(backdropUrl)
        view.showTitle(movie.title)
        view.showDescription(movie.overview)

        val readableDate = dateToHumanReadableUseCase.execute(movie.releaseDate)
        view.showReleaseDate(readableDate)
        view.showOriginalLanguage(movie.originalLanguage)
        view.showRating(movie.voteAverage.toString())
    }

}