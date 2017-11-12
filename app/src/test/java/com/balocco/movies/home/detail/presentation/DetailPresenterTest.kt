package com.balocco.movies.home.detail.presentation

import com.balocco.movies.common.UrlProvider
import com.balocco.movies.common.scheduler.TestSchedulerProvider
import com.balocco.movies.common.usecase.FetchGenresUseCase
import com.balocco.movies.common.usecase.ScreenSizeUseCase
import com.balocco.movies.data.model.Genre
import com.balocco.movies.data.model.Movie
import com.balocco.movies.data.store.GenreStore
import com.balocco.movies.home.detail.DetailContract
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

private const val SCREEN_WIDTH = 1920
private const val TITLE = "title"
private const val DESCRIPTION = "description"
private const val RELEASE_DATE = "Today"
private const val ORIGINAL_LANGUAGE = "english"
private const val RATING = 7.8
private const val URL_SUFFIX = "image.jpg"
private const val FULL_URL = "www.images.com/" + URL_SUFFIX
private const val GENRE_ID = "id"
private const val GENRE_NAME = "Name"

class DetailPresenterTest {

    @Mock private lateinit var movie: Movie
    @Mock private lateinit var view: DetailContract.View
    @Mock private lateinit var urlProvider: UrlProvider
    @Mock private lateinit var genreStore: GenreStore
    @Mock private lateinit var screenSizeUseCase: ScreenSizeUseCase
    @Mock private lateinit var dateToHumanReadableUseCase: DateToHumanReadableUseCase
    @Mock private lateinit var fetchGenresUseCase: FetchGenresUseCase

    private lateinit var releaseDate: Date
    private lateinit var presenter: DetailPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        releaseDate = Date()
        presenter = DetailPresenter(
                urlProvider,
                genreStore,
                TestSchedulerProvider(),
                screenSizeUseCase,
                dateToHumanReadableUseCase,
                fetchGenresUseCase)
        presenter.setView(view)
        prepareMockMovie()
        whenever(genreStore.hasData()).thenReturn(true)
    }

    @Test fun `When started with null movie, navigates back`() {
        presenter.start(null)

        verify(view).navigateBack()
    }

    @Test fun `When started with movie, sets title in view`() {
        presenter.start(movie)

        verify(view).setTitle(TITLE)
    }

    @Test fun `When started with movie, sets backdrop sizes to 16 by 9`() {
        whenever(screenSizeUseCase.screenWidth()).thenReturn(SCREEN_WIDTH)

        presenter.start(movie)

        val expectedHeight = SCREEN_WIDTH * 9 / 16
        verify(view).setBackdropSizes(SCREEN_WIDTH, expectedHeight)
    }

    @Test fun `When started with movie, shows movie backdrop`() {
        whenever(urlProvider.provideUrlForBackdrop(URL_SUFFIX)).thenReturn(FULL_URL)

        presenter.start(movie)

        verify(view).showBackdrop(FULL_URL)
    }

    @Test fun `When started with movie, shows movie title`() {
        presenter.start(movie)

        verify(view).showTitle(TITLE)
    }

    @Test fun `When started with movie, shows movie description`() {
        presenter.start(movie)

        verify(view).showDescription(DESCRIPTION)
    }

    @Test fun `When started with movie, shows movie release date`() {
        whenever(dateToHumanReadableUseCase.execute(releaseDate)).thenReturn(RELEASE_DATE)

        presenter.start(movie)

        verify(view).showReleaseDate(RELEASE_DATE)
    }

    @Test fun `When started with movie, shows movie original language`() {
        presenter.start(movie)

        verify(view).showOriginalLanguage(ORIGINAL_LANGUAGE)
    }

    @Test fun `When started with movie, shows movie rating`() {
        presenter.start(movie)

        verify(view).showRating(RATING.toString())
    }

    @Test fun `When started and genre store has data but no genres are found, shows empty genres in view`() {
        whenever(genreStore.hasData()).thenReturn(true)
        whenever(genreStore.genresFromIds(any())).thenReturn(emptyList())

        presenter.start(movie)

        verify(view).showGenres("")
    }

    @Test fun `When started and genre store has data and genres are found, shows genres returned from store`() {
        val genre = Genre(GENRE_ID, GENRE_NAME)
        whenever(genreStore.hasData()).thenReturn(true)
        whenever(genreStore.genresFromIds(any())).thenReturn(arrayListOf(genre))

        presenter.start(movie)

        verify(view).showGenres(genre.name)
    }

    @Test fun `When request for genres successful, shows genres in view`() {
        val genre = Genre(GENRE_ID, GENRE_NAME)
        whenever(genreStore.hasData()).thenReturn(false)
        whenever(fetchGenresUseCase.execute()).thenReturn(Completable.complete())
        whenever(genreStore.genresFromIds(any())).thenReturn(arrayListOf(genre))

        presenter.start(movie)

        verify(view).showGenres(genre.name)
    }

    @Test fun `When request for genres failing, shows no genres in view`() {
        whenever(genreStore.hasData()).thenReturn(false)
        whenever(fetchGenresUseCase.execute()).thenReturn(Completable.error(Throwable()))

        presenter.start(movie)

        verify(view).showGenres(DetailPresenter.EMPTY_GENRES)
    }

    private fun prepareMockMovie() {
        whenever(movie.title).thenReturn(TITLE)
        whenever(movie.overview).thenReturn(DESCRIPTION)
        whenever(movie.releaseDate).thenReturn(releaseDate)
        whenever(movie.originalLanguage).thenReturn(ORIGINAL_LANGUAGE)
        whenever(movie.voteAverage).thenReturn(RATING)
        whenever(movie.backdropPath).thenReturn(URL_SUFFIX)
    }
}