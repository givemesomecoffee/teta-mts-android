package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState.Companion.LOADED
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState.Companion.LOADING

class MovieDetailsViewModel : ViewModel() {
    private val domain: MovieCase = MovieCase()
    val data: LiveData<MovieUi> get() = _data
    private val _data = MutableLiveData<MovieUi>()
    val loadingState: LiveData<LoadingState> get() = _loadingState
    private val _loadingState = MutableLiveData<LoadingState>()

    fun getMovie(id: Int?) {
        viewModelScope.launch {
            try {
                _loadingState.value = LOADING
                withContext(Dispatchers.IO) { _data.postValue(id?.let { domain.getMovieById(it) }) }
                _loadingState.value = LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    fun init(movieId: Int?) {
        if (data.value == null) getMovie(movieId)
    }

}