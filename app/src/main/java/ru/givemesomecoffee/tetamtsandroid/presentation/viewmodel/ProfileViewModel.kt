package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi


class ProfileViewModel: ViewModel() {
    private val domain: UserCase = UserCase()
    val data: LiveData<UserUi> get() = _data
    private val _data = MutableLiveData<UserUi>()
    fun getUser(id: Int = 0) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { _data.postValue(domain.getUser(id)) }
        }
    }
}