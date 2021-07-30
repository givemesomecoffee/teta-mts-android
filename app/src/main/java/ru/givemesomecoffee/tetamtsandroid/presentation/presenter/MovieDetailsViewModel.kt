package ru.givemesomecoffee.tetamtsandroid.presentation.presenter

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


    fun getMovie(id: Int?, view: MovieDetailsFragment) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            view.onGetDataFailure(throwable)
        }
        viewModelScope.launch(handler) {
            withContext(Dispatchers.IO) { _data.postValue(domain.getMovieById(id!!)) }
        }
    }
    interface ErrorHandler{
        fun onGetDataFailure(e: Throwable)
    }

}