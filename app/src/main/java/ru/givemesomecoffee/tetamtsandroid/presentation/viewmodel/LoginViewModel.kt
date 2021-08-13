package ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi

class LoginViewModel : ViewModel() {
    private val domain: UserCase = UserCase()

    /*feels like bad solution*/

    val data: LiveData<UserUi> get() = _data
    private val _data = MutableLiveData<UserUi>()

    private fun getUser(id: Int) {
        Log.d("vm", "getting new user")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                _data.postValue(domain.getUser(id))
                Log.d("vm", domain.getUser(id).toString() + "new User in ld")
            }
        }
    }

    fun changeToken(token: String?, id: Int) {
        domain.changeToken(token, id)
        Log.d("vm", "token saved")

    }

    fun getUserId(token: String?) {
        Log.d("vm", "getUserId")
        val id = domain.getUserId(token)
        Log.d("vm", id.toString())
        if (id != null) {
            Log.d("vm", "view model data replaced")
            getUser(id)
            Log.d("vm", "view model data replaced")
        }


    }

    override fun onCleared() {
        Log.d("vm", "im cleared")
        //  data.value?.id?.let { changeToken(null, it) }
        super.onCleared()
    }

}