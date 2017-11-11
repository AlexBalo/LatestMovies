package com.balocco.movies.home.detail.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.balocco.movies.R
import com.balocco.movies.common.ui.BaseFragment
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.detail.DetailContract
import com.balocco.movies.home.detail.presentation.DetailPresenter
import javax.inject.Inject

class DetailFragment : BaseFragment(),
        DetailContract.View {

    companion object {
        private const val KEY_MOVIE = "KEY_MOVIE"

        fun newInstance(movie: Movie): Fragment =
                DetailFragment().apply {
                    val args = Bundle()
                    args.putParcelable(KEY_MOVIE, movie)
                    arguments = args
                }
    }

    @Inject lateinit var presenter: DetailPresenter

    private lateinit var container: FragmentContainer

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = when (parentFragment) {
            null -> context as FragmentContainer
            else -> parentFragment as FragmentContainer
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        bindView(this, view)

        this.container.enableNavigation(true)

        val movie: Movie? = arguments?.getParcelable(KEY_MOVIE)
        presenter.setView(this)
        presenter.start(movie)

        return view
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setTitle(title: String) {
        container.setTitle(title)
    }

    override fun navigateBack() {
        container.back()
    }

    interface FragmentContainer {

        fun setTitle(title: CharSequence)

        fun enableNavigation(enabled: Boolean)

        fun back()

    }

}