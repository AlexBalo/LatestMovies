package com.balocco.movies.home.detail.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.balocco.movies.R
import com.balocco.movies.common.image.ImageLoader
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
    @Inject lateinit var imageLoader: ImageLoader

    @BindView(R.id.iv_backdrop) lateinit var ivBackdrop: ImageView
    @BindView(R.id.tv_title) lateinit var tvTitle: TextView
    @BindView(R.id.tv_genres) lateinit var tvGenres: TextView
    @BindView(R.id.tv_description) lateinit var tvDescription: TextView
    @BindView(R.id.tv_released) lateinit var tvReleased: TextView
    @BindView(R.id.tv_original_language) lateinit var tvLanguage: TextView
    @BindView(R.id.tv_rating) lateinit var tvRating: TextView

    private var container: FragmentContainer? = null

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

        this.container?.enableNavigation(true)

        val movie: Movie? = arguments?.getParcelable(KEY_MOVIE)
        presenter.setView(this)
        presenter.start(movie)

        return view
    }

    override fun onDestroy() {
        presenter.destroy()
        container = null
        super.onDestroy()
    }

    override fun setTitle(title: String) {
        container?.setTitle(title)
    }

    override fun navigateBack() {
        container?.back()
    }

    override fun setBackdropSizes(width: Int, height: Int) {
        val params = ivBackdrop.layoutParams
        params.width = width
        params.height = height
    }

    override fun showBackdrop(backdropUrl: String) {
        imageLoader.loadImageInView(backdropUrl, ivBackdrop)
    }

    override fun showTitle(title: String) {
        tvTitle.text = title
    }

    override fun showGenres(genres: String) {
        tvGenres.text = genres
    }

    override fun showDescription(description: String) {
        tvDescription.text = description
    }

    override fun showReleaseDate(releaseDate: String) {
        tvReleased.text = context?.getString(R.string.popular_item_release, releaseDate)
    }

    override fun showOriginalLanguage(language: String) {
        tvLanguage.text = context?.getString(R.string.popular_item_language, language)
    }

    override fun showRating(rating: String) {
        tvRating.text = context?.getString(R.string.popular_item_rating, rating)
    }

    interface FragmentContainer {

        fun setTitle(title: CharSequence)

        fun enableNavigation(enabled: Boolean)

        fun back()

    }

}