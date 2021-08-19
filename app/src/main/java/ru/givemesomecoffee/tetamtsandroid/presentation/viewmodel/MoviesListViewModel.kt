package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import javax.inject.Inject

class MoviesListViewModel(private val domain: MoviesListCases) : ViewModel() {

    val data: LiveData<List<MovieUi>> get() = _data
    private val _data = MutableLiveData<List<MovieUi>>()
    val categories: LiveData<List<CategoryUi>> get() = _categories
    private val _categories = MutableLiveData<List<CategoryUi>>()
    val loadingState: LiveData<LoadingState> get() = _loadingState
    private val _loadingState = MutableLiveData<LoadingState>()

    fun init() {
        if (categories.value == null)
            updateCategories()
        if (data.value == null)
            updateMoviesListByCategory()
    }

    private val categoriesHandler = CoroutineExceptionHandler { _, e ->
        Log.d("CategoriesList", e.toString())
        viewModelScope.launch {
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

    fun updateMoviesListByCategory(categoryId: Int? = null) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("test", Thread.currentThread().toString())
                try {
                    _loadingState.postValue(LoadingState.LOADING)
                    _data.postValue(domain.getMoviesList(categoryId))
                    _loadingState.postValue(LoadingState.LOADED)
                } catch (e: Exception) {
                    _loadingState.postValue(LoadingState.error(e.message))
                }
            }
        }
    }

}

class MoviesListViewModelFactory @Inject constructor(
    private val domain: MoviesListCases
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //used toString() cause flat compare returns false,
        // cant figure why(feels like it compares generic string of model class???)
        require(modelClass.toString() == MoviesListViewModel::class.toString())
        return MoviesListViewModel(domain) as T
    }

/*    @AssistedFactory
    interface Factory {
        fun create(@Assisted("movieId") movieId: Int): MovieDetailsViewModelFactory
    }*/
}