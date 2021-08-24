package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.data.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.App

class RegisterViewModel : ViewModel() {
    private val domain = App.appComponent.moviesListCase() //TODO: rework
    val data: LiveData<List<CategoryUi>> get() = _data
    private val _data = MutableLiveData<List<CategoryUi>>()

    private fun getCategories() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _data.postValue(domain.getCategoriesList())
            }
        }
    }

    fun init(){
        if (data.value == null){
            getCategories()
        }
    }
}