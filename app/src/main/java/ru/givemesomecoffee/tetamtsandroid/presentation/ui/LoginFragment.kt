package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.Login

class LoginFragment : Fragment() {
    private var login: Login? = null
    private val userCase = UserCase() //TODO: remove dependency
    private var confirmLoginButton: MaterialButton? = null
    private var emailView: TextInputLayout? = null
    private var passwordView: TextInputLayout? = null
    private var errorEmptyView: TextView? = null
    private var errorWrongDataView: TextView? = null
    private var toRegisterButtonView: MaterialButton? = null

    private fun init() {
        confirmLoginButton = requireView().findViewById(R.id.confirm_login_button)
        emailView = requireView().findViewById(R.id.profile_email_input)
        passwordView = requireView().findViewById(R.id.profile_password_input)
        errorEmptyView = requireView().findViewById(R.id.login_error_empty)
        errorWrongDataView = requireView().findViewById(R.id.login_error_wrong_data)
        toRegisterButtonView = requireView().findViewById(R.id.to_register_button)

        confirmLoginButton?.setOnClickListener {
            clearErrors()
            checkUser()
        }

        toRegisterButtonView?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDetach() {
        login = null
        super.onDetach()
    }

    private fun checkUser() {
        val email = emailView?.editText?.text.toString()
        val password = passwordView?.editText?.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            errorEmptyView?.visibility = View.VISIBLE
        } else {
            val check: Int? = userCase.checkUser(email, password)
            if (check != null) {
                login?.saveLogin(check, getToken())
                login?.showProfile()
            } else {
                errorWrongDataView?.visibility = View.VISIBLE
            }
        }
    }

    private fun clearErrors() {
        errorEmptyView?.visibility = View.INVISIBLE
        errorWrongDataView?.visibility = View.INVISIBLE
    }

    private fun getToken(length: Int = 16): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}