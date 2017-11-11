package com.balocco.movies.home.presentation

import android.os.Bundle
import com.balocco.movies.R
import com.balocco.movies.home.MainContract
import com.balocco.movies.mvp.ReactivePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
) : ReactivePresenter(), MainContract.Presenter {

    private lateinit var view: MainContract.View

    override fun setView(view: MainContract.View) {
        this.view = view
    }

    override fun start(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            view.setTitle(R.string.title_popular_feed)
            view.showPopular()
            return
        }
    }

}