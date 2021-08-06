package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi

class MoviesListViewModel : ViewModel() {
    private val domain: MoviesListCases = MoviesListCases()
    val data: LiveData<List<MovieUi>> get() = _data
    private val _data = MutableLiveData<List<MovieUi>>()
    val categories: LiveData<List<CategoryUi>> get() = _categories
    private val _categories = MutableLiveData<List<CategoryUi>>()
    val loadingState: LiveData<LoadingState> get() = _loadingState
    private val _loadingState = MutableLiveData<LoadingState>()

     fun init(){
         if(categories.value == null)
        updateCategories()
         if(data.value == null)
        updateMoviesListByCategory()
    }

    private val categoriesHandler = CoroutineExceptionHandler { _, e ->
        Log.d("CategoriesList", e.toString())
        viewModelScope.launch() {
            withContext(Dispatchers.IO) {
                delay(5000L)
                updateCategories()
            }
        }
    }

    private fun updateCategories() {
        viewModelScope.launch(categoriesHandler) {
            withContext(Dispatchers.IO) { _categories.postValue(domain.getCategoriesList()) }
        }
    }

    fun updateMoviesListByCategory(categoryId: Int = 0) {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                withContext(Dispatchers.IO) {
                    _data.postValue(domain.getMoviesList(categoryId))
                }
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

}