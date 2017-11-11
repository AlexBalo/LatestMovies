package com.balocco.movies.home

import android.os.Bundle
import android.support.annotation.StringRes
import com.balocco.movies.mvp.BasePresenter
import com.balocco.movies.mvp.BaseView

interface MainContract {

    interface Presenter : BasePresenter<View> {

        fun start(savedInstanceState: Bundle?)

    }

    interface View : BaseView {

        fun setTitle(@StringRes titleResource: Int)

        fun setTitle(title: CharSequence)

        fun showPopular()

    }

}