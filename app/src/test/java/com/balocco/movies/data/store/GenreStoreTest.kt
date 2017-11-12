package com.balocco.movies.data.store

import com.balocco.movies.data.model.Genre
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val ID = "1"
private const val NAME = "Genre"

class GenreStoreTest {

    @Mock private lateinit var genre: Genre

    private lateinit var store: GenreStore

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)
        store = GenreStore()
    }

    @Test fun `When store was never provided with data, returns is empty`() {
        assertThat(store.hasData()).isFalse()
    }

    @Test fun `When store was provided with data, returns is not empty`() {
        val genres = arrayListOf(genre)

        store.storeGenres(genres)

        assertThat(store.hasData()).isTrue()
    }

    @Test fun `When store asked for genres from ids but not genres found, returns empty list`() {
        whenever(genre.id).thenReturn("1")
        val genres = arrayListOf(genre)
        val ids = arrayListOf(3)
        store.storeGenres(genres)

        val foundGenres = store.genresFromIds(ids)

        assertThat(foundGenres).isEmpty()
    }

    @Test fun `When store asked for genres from ids and genres found, returns list`() {
        whenever(genre.id).thenReturn(ID)
        whenever(genre.name).thenReturn(NAME)
        val genres = arrayListOf(genre)
        val ids = arrayListOf(ID.toInt())
        store.storeGenres(genres)

        val foundGenres = store.genresFromIds(ids)

        assertThat(foundGenres[0].id).isEqualTo(ID)
        assertThat(foundGenres[0].name).isEqualTo(NAME)
    }
}