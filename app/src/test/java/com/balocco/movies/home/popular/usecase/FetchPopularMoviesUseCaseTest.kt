package com.balocco.movies.home.popular.usecase

import com.balocco.movies.data.model.responses.PopularResponse
import com.balocco.movies.data.remote.RemoteDataSource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val PAGE = 1
private const val ERROR_MESSAGE = "error"

class FetchPopularMoviesUseCaseTest {

    @Mock private lateinit var remoteDataSource: RemoteDataSource
    @Mock private lateinit var response: PopularResponse

    private lateinit var useCase: FetchPopularMoviesUseCase

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = FetchPopularMoviesUseCase(remoteDataSource)
    }

    @Test fun `When fetch from remote data source invoked, source triggers request`() {
        whenever(remoteDataSource.getPopularMovies(any())).thenReturn(Single.just(response))

        useCase.execute(PAGE).test()

        verify(remoteDataSource).getPopularMovies(PAGE)
    }

    @Test fun `When fetched from remote data source, response is returned successfully`() {
        whenever(remoteDataSource.getPopularMovies(any())).thenReturn(Single.just(response))

        useCase.execute(PAGE)
                .test()
                .assertNoErrors()
                .assertValue(response)
    }

    @Test fun `When failed to fetched from remote data source, returns error with message`() {
        whenever(remoteDataSource.getPopularMovies(any())).thenReturn(Single.error(Throwable(ERROR_MESSAGE)))

        val testObserver = useCase.execute(PAGE).test()

        assertThat(testObserver.errors()[0].message).isEqualTo(ERROR_MESSAGE)
    }

}