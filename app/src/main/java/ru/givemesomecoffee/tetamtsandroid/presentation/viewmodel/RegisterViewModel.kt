package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class RegisterViewModel : ViewModel() {
    private val domain = MoviesListCases()
    val data: LiveData<List<CategoryUi>> get() = _data
    private val _data = MutableLiveData<List<CategoryUi>>()

    fun getCategories() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _data.postValue(domain.getCategoriesList())
            }
        }
    }
}