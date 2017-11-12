package com.balocco.movies.home.presentation

import android.os.Bundle
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.MainContract
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock private lateinit var view: MainContract.View
    @Mock private lateinit var savedInstanceState: Bundle
    @Mock private lateinit var movie: Movie

    private lateinit var presenter: MainPresenter

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter()
        presenter.setView(view)
    }

    @Test fun `When started with null bundle, shows popular`() {
        presenter.start(null)

        verify(view).showPopular()
    }

    @Test fun `When started with not null bundle, doesn't shows popular`() {
        presenter.start(savedInstanceState)

        verify(view, never()).showPopular()
    }

    @Test fun `When movie selected, shows movie detail`() {
        presenter.onMovieSelected(movie)

        verify(view).showMovieDetail(movie)
    }
}