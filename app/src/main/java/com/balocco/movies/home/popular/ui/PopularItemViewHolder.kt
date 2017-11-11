package com.balocco.movies.home.popular.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.balocco.movies.R
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularItemContract
import com.balocco.movies.home.popular.presentation.PopularItemPresenter

class PopularItemViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView), PopularItemContract.View {

    @BindView(R.id.tv_movie_title) lateinit var tvTitle: TextView

    private var presenter: PopularItemPresenter

    init {
        ButterKnife.bind(this, itemView)

        presenter = PopularItemPresenter()
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
}