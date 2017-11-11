package com.balocco.movies.home.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.balocco.movies.R
import com.balocco.movies.common.extension.replaceFragment
import com.balocco.movies.common.ui.BaseActivity
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.MainContract
import com.balocco.movies.home.detail.ui.DetailFragment
import com.balocco.movies.home.popular.ui.PopularFragment
import com.balocco.movies.home.presentation.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
        PopularFragment.FragmentContainer,
        DetailFragment.FragmentContainer,
        MainContract.View,
        View.OnClickListener {

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter.setView(this)
        presenter.start(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == android.R.id.home) {
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!fragmentManager.popBackStackImmediate()) {
            super.onBackPressed()
        }
    }

    override fun showPopular() {
        showFragment(PopularFragment.newInstance())
    }

    override fun showMovieDetail(movie: Movie) {
        showFragment(DetailFragment.newInstance(movie), true)
    }

    override fun onMovieSelected(movie: Movie) {
        presenter.onMovieSelected(movie)
    }

    override fun enableNavigation(enabled: Boolean) {
        supportActionBar?.let {
            it.setHomeButtonEnabled(enabled)
            it.setDisplayHomeAsUpEnabled(enabled)
            it.setDisplayShowHomeEnabled(enabled)
        }
    }

    override fun back() {
        onBackPressed()
    }

    private fun showFragment(fragment: Fragment,
                             addToBackStack: Boolean = false) =
            supportFragmentManager.replaceFragment(R.id.fragment_container, fragment, addToBackStack)
}
