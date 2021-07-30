package ru.givemesomecoffee.tetamtsandroid.presentation.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragment

class MoviesListViewModel: ViewModel() {
    private val domain: MoviesListCases = MoviesListCases()
    val data: LiveData<List<MovieUi>> get() = _data
    private val _data = MutableLiveData<List<MovieUi>>()
    val categories: LiveData<List<CategoryUi>> get() = _categories
    private val _categories = MutableLiveData<List<CategoryUi>>()

    private val categoriesHandler = CoroutineExceptionHandler { _, e ->
      Log.d("CategoriesList", e.toString())

    }

    private val moviesHandler = CoroutineExceptionHandler { _, exception ->
       // view.onGetDataFailure(exception.message)
    }

    fun updateCategories(view: MoviesListFragment) {
        view.viewLifecycleOwner.lifecycleScope.launch(categoriesHandler) {
            withContext(Dispatchers.IO) { _categories.postValue(domain.getCategoriesList()) }
        }
    }

    fun updateMoviesListByCategory(categoryId: Int, view: MoviesListFragment) {
        view.viewLifecycleOwner.lifecycleScope.launch(moviesHandler) {
            withContext(Dispatchers.IO) { _data.postValue(domain.getMoviesList(categoryId)) }
        }
    }

}