package com.balocco.movies.common.ui

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    private lateinit var unbinder: Unbinder

    @SuppressLint("CheckResult")
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbindView()
    }

    protected fun bindView(target: Any, source: View) {
        unbinder = ButterKnife.bind(target, source)
    }

    private fun unbindView() {
        unbinder.unbind()
    }

}