package com.balocco.movies.home.popular.presentation

import android.os.Bundle
import com.balocco.movies.R
import com.balocco.movies.common.scheduler.TestSchedulerProvider
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.popular.PopularContract
import com.balocco.movies.home.popular.usecase.FetchPopularMoviesUseCase
import com.balocco.movies.home.popular.usecase.SortPopularMoviesUseCase
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PopularPresenterTest {

    @Mock private lateinit var fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
    @Mock private lateinit var sortPopularMoviesUseCase: SortPopularMoviesUseCase
    @Mock private lateinit var view: PopularContract.View
    @Mock private lateinit var savedInstanceState: Bundle
    @Mock private lateinit var movie: Movie

    private lateinit var movies: ArrayList<Movie>
    private lateinit var presenter: PopularPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        movies = arrayListOf(movie)
        presenter = PopularPresenter(
                TestSchedulerProvider(),
                fetchPopularMoviesUseCase,
                sortPopularMoviesUseCase)
        presenter.setView(view)
    }

    @Test fun `When started, sets title in view`() {
        initilizePresenterWithMovies()

        presenter.start(savedInstanceState)

        verify(view).setTitle(R.string.title_popular_feed)
    }

    @Test fun `When started and movies already fetched, hides loading`() {
        initilizePresenterWithMovies()

        presenter.start(savedInstanceState)

        verify(view).hideLoading()
    }

    @Test fun `When started and movies already fetched, shows movies`() {
        initilizePresenterWithMovies()

        presenter.start(savedInstanceState)

        verify(view).showMovies(movies)
    }

    @Test fun `When started and descending release date filter, shows filter message`() {
        initilizePresenterWithMovies()
        whenever(savedInstanceState.getInt(PopularPresenter.KEY_FILTER)).thenReturn(2)

        presenter.start(savedInstanceState)

        verify(view).showFilterMessage(R.string.popular_filter_message_desc)
    }

    @Test fun `When started and ascending release date filter, shows filter message`() {
        initilizePresenterWithMovies()
        whenever(savedInstanceState.getInt(PopularPresenter.KEY_FILTER)).thenReturn(1)

        presenter.start(savedInstanceState)

        verify(view).showFilterMessage(R.string.popular_filter_message_asc)
    }

    @Test fun `When stopped, hides filter message`() {
        presenter.stop()

        verify(view).hideFilterMessage()
    }

    @Test fun `When movie selected, hides filter message`() {
        presenter.onMovieSelected(movie)

        verify(view).hideFilterMessage()
    }

    @Test fun `When movie selected, navigates to movie detail`() {
        presenter.onMovieSelected(movie)

        verify(view).navigateToDetail(movie)
    }

    private fun initilizePresenterWithMovies() {
        whenever(savedInstanceState.getParcelableArrayList<Movie>(PopularPresenter.KEY_MOVIES)).thenReturn(movies)
    }
}