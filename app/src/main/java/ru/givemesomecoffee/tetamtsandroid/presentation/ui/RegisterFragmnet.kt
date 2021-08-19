package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.entity.CategoryUi
import ru.givemesomecoffee.tetamtsandroid.domain.entity.UserUi
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.Login
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.RegisterViewModel
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter.CategoryFavouriteAdapter
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.utils.RecyclerItemDecoration

class RegisterFragment : Fragment() {
    private var login: Login? = null
    private val userCase = App.appComponent.userCase() //TODO: remove
    private val viewModel: RegisterViewModel by viewModels()
    private var favouriteCategoriesList: RecyclerView? = null
    private var favouriteCategoriesListAdapter: CategoryFavouriteAdapter? = null
    private var errorWrongDataView: TextView? = null
    private var nameView: TextInputLayout? = null
    private var nameInput: TextInputEditText? = null
    private var passwordView: TextInputLayout? = null
    private var passwordInput: TextInputEditText? = null
    private var emailView: TextInputLayout? = null
    private var emailInput: TextInputEditText? = null
    private var confirmLoginButton: MaterialButton? = null
    private val favouriteCategories: MutableList<Int> = mutableListOf()


    private fun init() {
        confirmLoginButton = requireView().findViewById(R.id.confirm_login_button)
        emailView = requireView().findViewById(R.id.profile_email_input)
        passwordView = requireView().findViewById(R.id.profile_password_input)
        nameView = requireView().findViewById(R.id.profile_name_input)
        errorWrongDataView = requireView().findViewById(R.id.login_error_wrong_data)
        favouriteCategoriesList = requireView().findViewById(R.id.profile_favourite_list)
        viewModel.init()
        viewModel.data.observe(viewLifecycleOwner, Observer(::bindCategories))
        favouriteCategoriesList!!.addItemDecoration(RecyclerItemDecoration(6, 0, 20))
        confirmLoginButton?.setOnClickListener {
            clearErrors()
            checkUser()
        }
        nameInput = requireView().findViewById(R.id.profile_name_input_edit)
        passwordInput = requireView().findViewById(R.id.profile_password_input_edit)
        emailInput = requireView().findViewById(R.id.profile_email_input_edit)
        nameInput!!.doOnTextChanged { text, start, before, count -> validateUserName() }
        passwordInput!!.doOnTextChanged { text, start, before, count -> validatePassword() }
        emailInput!!.doOnTextChanged { text, start, before, count -> validateEmail() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Login)
            login = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDetach() {
        login = null
        super.onDetach()
    }


    private fun bindCategories(list: List<CategoryUi>?) {
        favouriteCategoriesListAdapter =
            list?.let {
                CategoryFavouriteAdapter(
                    it,
                    itemClick = { categoryId: Int -> onCategoryClicked(categoryId) })
            }

        favouriteCategoriesList!!.adapter = favouriteCategoriesListAdapter
    }

    private fun onCategoryClicked(categoryId: Int) {
        try {
            favouriteCategories.first { it == categoryId }
            favouriteCategories.remove(categoryId)
        } catch (e: NoSuchElementException) {
            favouriteCategories.add(categoryId)
        }
    }

    private fun checkUser() {
        if (validateEmail() && validatePassword() && validateUserName()) {
            val email = emailView?.editText?.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val check: Int? = userCase.checkUser(email)
                    if (check != null) {
                        withContext(Dispatchers.Main) {
                            errorWrongDataView?.visibility = View.VISIBLE
                        }
                    } else {
                        val name = nameView?.editText?.text.toString()

                        val password = passwordView?.editText?.text.toString()
                        val id =
                            userCase.saveNewUser(
                                UserUi(
                                    name = name,
                                    email = email,
                                    password = password
                                )
                            )
                        if (favouriteCategories.size > 0) {
                            userCase.setFavouriteCategories(categories = favouriteCategories, id)
                        }
                        withContext(Dispatchers.Main) { login?.onRegisterComplete() }
                    }
                }
            }
        }
    }

    private fun clearErrors() {
        errorWrongDataView?.visibility = View.INVISIBLE
    }

    private fun validateEmail(): Boolean {
        val email = emailView?.editText?.text.toString()
        if (email.isEmpty()) {
            emailView?.error = getString(R.string.register_empty_email_error_text)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailView?.error = getString(R.string.register_wrong_email_error_text)
            return false
        }
        emailView?.error = null
        return true
    }

    private fun validatePassword(): Boolean {
        val password = passwordView?.editText?.text.toString()
        if (password.isEmpty()) {
            passwordView?.error = getString(R.string.register_empty_password_error_text)
            return false
        } else if (password.length < 6) {
            passwordView?.error = getString(R.string.register_short_password_error_text)
            return false
        }
        passwordView?.error = null
        return true
    }

    private fun validateUserName(): Boolean {
        val name = nameView?.editText?.text.toString()
        if (name.isEmpty()) {
            nameView?.error = getString(R.string.register_empty_name_error_text)
            return false
        }
        nameView?.error = null
        return true
    }

}