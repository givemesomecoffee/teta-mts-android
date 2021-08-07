package ru.givemesomecoffee.tetamtsandroid.data.repository

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.givemesomecoffee.tetamtsandroid.data.assets.movies.MoviesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class RepositoryTest {

    private val movies: List<Movie> =
        MoviesDataSourceImpl().getMovies().sortedBy { it.id }
    private val moviesUi: List<MovieUi> = MoviesMapper().toMovieUi(movies)
    private lateinit var fakeDatasource: FakeDataSource
    private lateinit var repository: Repository

    @Before
    fun createRepository() {
        fakeDatasource = FakeDataSource(movies.toMutableList())
        repository = Repository(fakeDatasource)

    }

    @Test
    fun getMovies() {
        val result = repository.getMoviesList()
        assertEquals(moviesUi.size, result.size)
        for (i in moviesUi.indices){
            if (moviesUi[i] != result[i]){
                assertFalse("objects are different!", moviesUi[i] == result[i] )
            }
        }
    }

}