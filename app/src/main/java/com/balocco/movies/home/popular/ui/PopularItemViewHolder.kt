package com.balocco.movies.home.popular.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.balocco.movies.R
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularItemContract
import com.balocco.movies.home.popular.presentation.PopularItemPresenter
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase

class PopularItemViewHolder(
        itemView: View,
        private val dateToHumanReadableUseCase: DateToHumanReadableUseCase
) : RecyclerView.ViewHolder(itemView), PopularItemContract.View {

    @BindView(R.id.tv_title) lateinit var tvTitle: TextView
    @BindView(R.id.tv_released) lateinit var tvReleased: TextView
    @BindView(R.id.tv_original_language) lateinit var tvLanguage: TextView
    @BindView(R.id.tv_rating) lateinit var tvRating: TextView

    private val context: Context
    private val presenter: PopularItemPresenter

    init {
        ButterKnife.bind(this, itemView)

        context = itemView.context
        presenter = PopularItemPresenter(dateToHumanReadableUseCase)
        presenter.setView(this)
    }

    fun onBindViewHolder(movie: Movie) {
        presenter.onMovieUpdated(movie)
    }

    fun onViewRecycled() {
        presenter.destroy()
    }

    override fun showTitle(title: String) {
        tvTitle.text = title
    }

    override fun showReleaseDate(releaseDate: String) {
        tvReleased.text = context.getString(R.string.popular_item_release, releaseDate)
    }

    override fun showOriginalLanguage(language: String) {
        tvLanguage.text = context.getString(R.string.popular_item_language, language)
    }

    override fun showRating(rating: String) {
        tvRating.text = context.getString(R.string.popular_item_rating, rating)
    }
}