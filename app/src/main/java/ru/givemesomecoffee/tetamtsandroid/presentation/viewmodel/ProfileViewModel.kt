package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.data.entity.UserUi
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase

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