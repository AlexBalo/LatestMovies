package com.balocco.movies.home.popular.presentation

import com.balocco.movies.common.UrlProvider
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularItemContract
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

private const val TITLE = "title"
private const val POPULARITY = 97.44
private const val RELEASE_DATE = "Today"
private const val ORIGINAL_LANGUAGE = "english"
private const val RATING = 7.8
private const val URL_SUFFIX = "image.jpg"
private const val FULL_URL = "www.images.com/" + URL_SUFFIX

class PopularItemPresenterTest {

    @Mock private lateinit var movie: Movie
    @Mock private lateinit var view: PopularItemContract.View
    @Mock private lateinit var urlProvider: UrlProvider
    @Mock private lateinit var dateToHumanReadableUseCase: DateToHumanReadableUseCase

    private lateinit var releaseDate: Date
    private lateinit var presenter: PopularItemPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        releaseDate = Date()
        presenter = PopularItemPresenter(
                urlProvider,
                dateToHumanReadableUseCase)
        presenter.setView(view)
        prepareMockMovie()
    }

    @Test fun `When movie updated, shows movie title in view`() {
        presenter.onMovieUpdated(movie)

        verify(view).showTitle(TITLE)
    }

    @Test fun `When movie updated, shows movie popularity in view`() {
        presenter.onMovieUpdated(movie)

        verify(view).showPopularity(POPULARITY.toString())
    }

    @Test fun `When movie updated, shows movie readable release date in view`() {
        whenever(dateToHumanReadableUseCase.execute(releaseDate)).thenReturn(RELEASE_DATE)

        presenter.onMovieUpdated(movie)

        verify(view).showReleaseDate(RELEASE_DATE)
    }

    @Test fun `When movie updated, shows movie original language in view`() {
        presenter.onMovieUpdated(movie)

        verify(view).showOriginalLanguage(ORIGINAL_LANGUAGE)
    }

    @Test fun `When movie updated, shows movie rating in view`() {
        presenter.onMovieUpdated(movie)

        verify(view).showRating(RATING.toString())
    }

    @Test fun `When movie updated, shows movie poster in view`() {
        whenever(urlProvider.provideUrlForPoster(URL_SUFFIX)).thenReturn(FULL_URL)

        presenter.onMovieUpdated(movie)

        verify(view).showPoster(FULL_URL)
    }

    private fun prepareMockMovie() {
        whenever(movie.title).thenReturn(TITLE)
        whenever(movie.popularity).thenReturn(POPULARITY)
        whenever(movie.releaseDate).thenReturn(releaseDate)
        whenever(movie.originalLanguage).thenReturn(ORIGINAL_LANGUAGE)
        whenever(movie.voteAverage).thenReturn(RATING)
        whenever(movie.posterPath).thenReturn(URL_SUFFIX)
    }
}