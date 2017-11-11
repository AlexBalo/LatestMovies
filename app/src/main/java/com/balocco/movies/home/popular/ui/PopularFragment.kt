package com.balocco.movies.home.popular.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import butterknife.BindView
import com.balocco.movies.R
import com.balocco.movies.common.UrlProvider
import com.balocco.movies.common.image.ImageLoader
import com.balocco.movies.common.ui.BaseFragment
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularContract
import com.balocco.movies.home.popular.presentation.PopularPresenter
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase
import javax.inject.Inject

class PopularFragment : BaseFragment(),
        PopularContract.View {

    companion object {
        fun newInstance(): Fragment = PopularFragment()
    }

    @Inject lateinit var presenter: PopularPresenter
    @Inject lateinit var urlProvider: UrlProvider
    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var dateToHumanReadableUseCase: DateToHumanReadableUseCase

    @BindView(R.id.pb_loader) lateinit var progressBar: ProgressBar
    @BindView(R.id.rv_moview) lateinit var recyclerView: RecyclerView

    private lateinit var adapter: PopularAdapter
    private lateinit var container: FragmentContainer

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = when (parentFragment) {
            null -> context as FragmentContainer
            else -> parentFragment as FragmentContainer
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        bindView(this, view)

        recyclerView.isClickable = true
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        context?.let {
            adapter = PopularAdapter(it,
                    imageLoader,
                    urlProvider,
                    dateToHumanReadableUseCase)
            recyclerView.adapter = adapter
        }

        presenter.setView(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showMovies(movies: List<Movie>) {
        adapter.items = movies
    }

    interface FragmentContainer {

        fun onMovieSelected(movie: Movie)

    }

}