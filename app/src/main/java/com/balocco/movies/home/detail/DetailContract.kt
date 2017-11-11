package com.balocco.movies.home.detail

import com.balocco.movies.data.model.Movie
import com.balocco.movies.mvp.BasePresenter
import com.balocco.movies.mvp.BaseView

interface DetailContract {

    interface Presenter : BasePresenter<View> {

        fun start(movie: Movie?)

    }

    interface View : BaseView {

        fun setTitle(title: String)

        fun navigateBack()

        fun setBackdropSizes(width: Int, height: Int)

        fun showBackdrop(backdropUrl: String)

        fun showTitle(title: String)

        fun showDescription(description: String)

        fun showReleaseDate(releaseDate: String)

        fun showOriginalLanguage(language: String)

        fun showRating(rating: String)

    }

}