package com.balocco.movies.mvp

interface BasePresenter<V> {

    fun setView(view: V)

}