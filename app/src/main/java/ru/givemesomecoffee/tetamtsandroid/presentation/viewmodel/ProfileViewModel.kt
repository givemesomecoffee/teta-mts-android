package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase


class ProfileViewModel: ViewModel() {
    private val domain: UserCase = UserCase()
    val data: LiveData<List<UserWithFavourites>> get() = _data
    private val _data = MutableLiveData<List<UserWithFavourites>>()
    fun getUser(id: Int? = 0) {
        viewModelScope.launch {


                withContext(Dispatchers.IO) { _data.postValue(domain.getUser(0)) }

        }
    }

    fun init(movieId: Int?) {
        if (data.value == null) getUser(movieId)
    }

}