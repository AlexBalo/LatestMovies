package com.balocco.movies.home.popular.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ProgressBar
import butterknife.BindView
import com.balocco.movies.R
import com.balocco.movies.common.UrlProvider
import com.balocco.movies.common.image.ImageLoader
import com.balocco.movies.common.ui.BaseFragment
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.OnEndlessListListener
import com.balocco.movies.home.popular.OnMovieClickListener
import com.balocco.movies.home.popular.OnRecyclerViewScrollListener
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

    @BindView(R.id.pb_loader) lateinit var pbLoading: ProgressBar
    @BindView(R.id.rv_moview) lateinit var rvMoview: RecyclerView

    private lateinit var adapter: PopularAdapter
    private lateinit var scrollListener: OnRecyclerViewScrollListener
    private lateinit var layoutManager: LinearLayoutManager
    private var container: FragmentContainer? = null
    private var snackBar: Snackbar? = null

    private val movieClickListener = object : OnMovieClickListener {
        override fun onMovieClicked(movie: Movie) {
            presenter.onMovieSelected(movie)
        }
    }

    private val endlessScrollListener = object : OnEndlessListListener {
        override fun onLoadMore() {
            presenter.onLoadMore()
        }
    }

    private val callback: Snackbar.Callback = object : Snackbar.Callback() {
        override fun onDismissed(snackbar: Snackbar?, event: Int) {
            presenter.onFilterCleared()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = when (parentFragment) {
            null -> context as FragmentContainer
            else -> parentFragment as FragmentContainer
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        bindView(this, view)

        this.container?.enableNavigation(false)

        rvMoview.isClickable = true
        layoutManager = LinearLayoutManager(context)
        scrollListener = OnRecyclerViewScrollListener(layoutManager)
        rvMoview.layoutManager = layoutManager
        rvMoview.addOnScrollListener(scrollListener)

        context?.let {
            adapter = PopularAdapter(it,
                    imageLoader,
                    urlProvider,
                    movieClickListener,
                    dateToHumanReadableUseCase)
            rvMoview.adapter = adapter
        }

        presenter.setView(this)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_popular, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.time_desc -> {
                    presenter.onSortByTimeDescending()
                    true
                }
                R.id.time_asc -> {
                    presenter.onSortByTimeAscending()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.destroy()
        container = null
        super.onDestroy()
    }

    override fun setTitle(titleRes: Int) {
        container?.setTitle(R.string.title_popular_feed)
    }

    override fun showLoading() {
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbLoading.visibility = View.GONE
    }

    override fun showMovies(movies: List<Movie>) {
        adapter.items = movies
    }

    override fun navigateToDetail(movie: Movie) {
        container?.onMovieSelected(movie)
    }

    override fun scrollListToTop() {
        layoutManager.scrollToPositionWithOffset(0, 0)
    }

    override fun showFilterEnabledMessage(messageRes: Int) {
        val isSnackBarShown = snackBar != null && snackBar!!.isShown
        if (isSnackBarShown) {
            snackBar!!.setText(messageRes)
            return
        }

        snackBar = Snackbar.make(rvMoview, messageRes, Snackbar.LENGTH_INDEFINITE)
        snackBar!!.setAction(R.string.popular_filter_message_button, {
            presenter.onFilterCleared()
        })
        snackBar!!.addCallback(callback)
        snackBar!!.show()
    }

    override fun hideFilterMessage() {
        snackBar?.removeCallback(callback)
        snackBar?.dismiss()
    }

    override fun enabledLoadingCallbacks() {
        scrollListener.setEndlessScrollListener(endlessScrollListener)
    }

    override fun disableLoadingCallbacks() {
        scrollListener.setEndlessScrollListener(null)
    }

    interface FragmentContainer {

        fun setTitle(@StringRes titleResource: Int)

        fun enableNavigation(enabled: Boolean)

        fun onMovieSelected(movie: Movie)

    }

}