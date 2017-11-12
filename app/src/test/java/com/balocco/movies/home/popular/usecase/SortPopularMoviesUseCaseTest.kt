package com.balocco.movies.home.popular.usecase

import com.balocco.movies.data.model.Movie
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SortPopularMoviesUseCaseTest {

    @Mock private lateinit var movie1: Movie
    @Mock private lateinit var movie2: Movie
    @Mock private lateinit var movie3: Movie

    private lateinit var useCase: SortPopularMoviesUseCase

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = SortPopularMoviesUseCase()
    }

    @Test fun `When comparing movies, result is a sorted list`() {
        whenever(movie1.title).thenReturn("A")
        whenever(movie2.title).thenReturn("B")
        whenever(movie3.title).thenReturn("C")
        val movies = arrayListOf(movie3, movie1, movie2)

        val testObserver = useCase.execute(movies, TestMovieTitleDescendingComparator())
                .test()
                .assertValueCount(1)

        val resultList = testObserver.values()[0]
        assertThat(resultList[0].title).isEqualTo("C")
        assertThat(resultList[1].title).isEqualTo("B")
        assertThat(resultList[2].title).isEqualTo("A")
    }

    class TestMovieTitleDescendingComparator : Comparator<Movie> {

        override fun compare(movie1: Movie, movie2: Movie): Int {
            val title1 = movie1.title
            val title2 = movie2.title
            return title2.compareTo(title1);
        }
    }
}