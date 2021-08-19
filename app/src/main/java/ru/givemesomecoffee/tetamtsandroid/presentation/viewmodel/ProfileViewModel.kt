package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class ProfileViewModel : ViewModel() {
    private val domain: UserCase = App.appComponent.userCase()
    val data: LiveData<UserUi> get() = _data
    private val _data = MutableLiveData<UserUi>()
    private fun getUser(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { _data.postValue(domain.getUser(id)) }
        }
    }

    fun initUser(id: Int) {
        if (data.value == null) {
            getUser(id)
        }
    }
}