package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import ru.givemesomecoffee.tetamtsandroid.data.mapper.MoviesMapper
import ru.givemesomecoffee.tetamtsandroid.data.remote.MoviesApiService
import ru.givemesomecoffee.tetamtsandroid.data.remote.entity.MoviesApiResponse
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import io.reactivex.disposables.CompositeDisposable


class MoviesListViewModel : ViewModel() {
    var compositeDisposable = CompositeDisposable()
    private val domain: MoviesListCases = MoviesListCases()
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
        val obs: Observable<List<MovieUi>> = domain.getMoviesList(categoryId)
        _loadingState.postValue(LoadingState.LOADING)
        val dis = obs
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _data.postValue(it)
                _loadingState.postValue(LoadingState.LOADED)
            }
        compositeDisposable.add(dis)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}