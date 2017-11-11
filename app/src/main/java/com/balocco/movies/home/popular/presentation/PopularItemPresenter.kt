package com.balocco.movies.home.popular.presentation

import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularItemContract
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

class PopularItemPresenter @Inject constructor(
        private val dateToHumanReadableUseCase: DateToHumanReadableUseCase
) : ReactivePresenter(), PopularItemContract.Presenter {

    private lateinit var view: PopularItemContract.View

    override fun setView(view: PopularItemContract.View) {
        this.view = view
    }

    override fun onMovieUpdated(movie: Movie) {
        val readableDate = dateToHumanReadableUseCase.execute(movie.releaseDate)

        view.showTitle(movie.title)
        view.showReleaseDate(readableDate)
        view.showOriginalLanguage(movie.originalLanguage)
        view.showRating(movie.voteAverage.toString())
    }

}