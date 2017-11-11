package com.balocco.movies.home.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.balocco.movies.R
import com.balocco.movies.common.extension.replaceFragment
import com.balocco.movies.common.ui.BaseActivity
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.MainContract
import com.balocco.movies.home.popular.ui.PopularFragment
import com.balocco.movies.home.presentation.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
        PopularFragment.FragmentContainer,
        MainContract.View {

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter.setView(this)
        presenter.start(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun showPopular() {
        showFragment(PopularFragment.newInstance())
    }

    override fun onMovieSelected(movie: Movie) {
        // TODO: open detail
    }

    private fun showFragment(fragment: Fragment,
                             addToBackStack: Boolean = false) =
            supportFragmentManager.replaceFragment(R.id.fragment_container, fragment, addToBackStack)
}
