package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.domain.entity.MovieUi
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState.Companion.LOADED
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.LoadingState.Companion.LOADING

class MovieDetailsViewModel(private val movieId: Int, private val domain: MovieCase) : ViewModel() {
    //  private val domain: MovieCase = App.appComponent.movieCase()
    val data: LiveData<MovieUi> get() = _data
    private val _data = MutableLiveData<MovieUi>()
    val loadingState: LiveData<LoadingState> get() = _loadingState
    private val _loadingState = MutableLiveData<LoadingState>()

    fun getMovie() {
        viewModelScope.launch {
            try {
                _loadingState.value = LOADING
                withContext(Dispatchers.IO) { _data.postValue(movieId.let { domain.getMovieById(it) }) }
                _loadingState.value = LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    fun init() {
        if (data.value == null) getMovie()
    }
}

class MovieDetailsViewModelFactory @AssistedInject constructor(
    @Assisted("movieId") private val movieId: Int,
    private val domain: MovieCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //used toString() cause flat compare returns false,
        // cant figure why(feels like it compares generic string of model class???)
        require(modelClass.toString() == MovieDetailsViewModel::class.toString())
        return MovieDetailsViewModel(movieId, domain) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("movieId") movieId: Int): MovieDetailsViewModelFactory
    }
}




