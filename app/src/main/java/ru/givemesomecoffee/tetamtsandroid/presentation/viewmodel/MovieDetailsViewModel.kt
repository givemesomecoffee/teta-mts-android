package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MovieDetailsFragment

class MovieDetailsViewModel : ViewModel() {
    private val domain: MovieCase = MovieCase()
    val data: LiveData<MovieUi> get() = _data
    private val _data = MutableLiveData<MovieUi>()


    fun getMovie(id: Int?, view: MovieDetailsFragment, restore: Boolean = false) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            view.onGetDataFailure(throwable)
        }
        viewModelScope.launch() {
            withContext(Dispatchers.IO) { _data.postValue(domain.getMovieById(id!!, restore)) }
        }
    }

    interface ErrorHandler {
        fun onGetDataFailure(e: Throwable)
    }

}