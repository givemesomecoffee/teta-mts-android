package ru.givemesomecoffee.tetamtsandroid.presentation.presenter

import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragment

class MoviesListPresenter(private val view: MoviesListFragment) {
    private val domain: MoviesListCases = MoviesListCases()

    private val categoriesHandler = CoroutineExceptionHandler { _, e ->
        Log.d("CategoriesList", e.stackTraceToString())
        view.viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) { delay(5000L) }
            withContext(Dispatchers.Main) { updateCategories() }
        }
    }

    private val moviesHandler = CoroutineExceptionHandler { _, exception ->
        view.onGetDataFailure(exception.message)
    }

    private fun getCategoriesAsync(): Deferred<List<CategoryUi>> {
        return view.viewLifecycleOwner.lifecycleScope.async(categoriesHandler) {
            withContext(Dispatchers.IO) { domain.getCategoriesList() }
        }
    }

    private fun getMoviesListAsync(categoryId: Int): Deferred<List<MovieUi>> {
        return view.viewLifecycleOwner.lifecycleScope.async(moviesHandler) {
            domain.getMoviesList(categoryId)
        }
    }

    fun updateCategories() {
        view.viewLifecycleOwner.lifecycleScope.launch(categoriesHandler) {
            withContext(Dispatchers.Main) { view.setNewCategoriesList(getCategoriesAsync().await()) }
        }
    }

    fun updateMoviesListByCategory(categoryId: Int) {
        view.viewLifecycleOwner.lifecycleScope.launch(moviesHandler) {
            withContext(Dispatchers.Main) { view.setNewMoviesList(getMoviesListAsync(categoryId).await()) }
        }
    }

}