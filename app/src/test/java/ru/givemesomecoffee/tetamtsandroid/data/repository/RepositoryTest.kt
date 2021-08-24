/*
package ru.givemesomecoffee.tetamtsandroid.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.givemesomecoffee.tetamtsandroid.data.localData.db.assets.CategoriesDataSourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.localData.db.assets.MoviesDataSourceImpl
import ru.givemesomecoffee.localdata.db.entity.CategoryDto
import ru.givemesomecoffee.tetamtsandroid.data.localData.db.entity.Movie
import ru.givemesomecoffee.tetamtsandroid.data.mapper.CategoriesMapper
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.data.entity.CategoryUi
import ru.givemesomecoffee.data.entity.MovieUi

class RepositoryTest {

    private val movies: List<Movie> =
        MoviesDataSourceImpl().getMovies().sortedBy { it.id }
    private val moviesUi: List<MovieUi> = MoviesMapper.toMovieUi(movies)

    private val categories: List<CategoryDto> =
        CategoriesDataSourceImpl().getCategories().sortedBy { it.id }
    private val categoriesUi: List<CategoryUi> = CategoriesMapper.toCategoryUi(categories)
    private lateinit var fakeDatasource: FakeDataSource
    private lateinit var repository: Repository

    @Before
    fun createRepository() {
        fakeDatasource = FakeDataSource(movies, categories)
        repository = Repository(fakeDatasource)

    }

    @Test
    fun getMovies() {
        val result = repository.getMoviesList()
        assertEquals(moviesUi.size, result.size)
        for (i in moviesUi.indices) {
            assertEquals(moviesUi[i], result[i])
        }
    }

    @Test
    fun getMovieById() {
        val result = repository.getMovie(5)
        val expected = moviesUi.first { it.id == 5 }
        expected.category = categoriesUi.first { it.id == expected.categoryId }.title
        assertEquals(expected, result)
    }

    @Test
    fun getMoviesByCategory() {
        val result = repository.getMoviesList(2)
        val expected = moviesUi.filter { it.categoryId == 2 }
        assertEquals(expected.size, result.size)
        for (i in expected.indices) {
            assertEquals(expected[i], result[i])
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAllCategories() = runBlockingTest {
        val result = repository.getCategoriesList()
        val expected = categoriesUi
        assertEquals(expected.size, result.size)
        for (i in expected.indices) {
            assertEquals(expected[i], result[i])
        }
    }

}

*/
