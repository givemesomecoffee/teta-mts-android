package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.Login

class LoginFragment : Fragment() {
    private var confirmLoginButton: MaterialButton? = null
    private var emailEditText: TextInputEditText? = null
    private var passwordEditText: TextInputEditText? = null
    private var errorEmptyView: TextView? = null
    private var errorWrongDataView: TextView? = null
    private var login: Login? = null

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
        confirmLoginButton = view.findViewById(R.id.confirm_login_button)
        emailEditText = view.findViewById(R.id.profile_email_input_edit)
        passwordEditText = view.findViewById(R.id.profile_password_input_edit)
        errorEmptyView = view.findViewById(R.id.login_error_empty)
        errorWrongDataView = view.findViewById(R.id.login_error_wrong_data)
        confirmLoginButton!!.setOnClickListener {
            clearErrors()
            checkUser()
        }
    }

    private fun checkUser() {
        val email = emailEditText?.editableText.toString()
        Log.d("login",  emailEditText?.editableText.toString())
        val password = passwordEditText?.editableText.toString()
      //  Log.d("login",  passwordEditText?.editableText.toString().toInt().toString())
        if (email.isEmpty() || password.isEmpty()) {
            Log.d("login", "smth is empty")
            errorEmptyView?.visibility = View.VISIBLE
        } else {
            val check: Int? = UserCase().checkUser(email, password.toInt())
            if (check != null) {
                login?.saveLogin(check)
                login?.showProfile()
            } else {
                Log.d("login", "smth is wrong")
                errorWrongDataView?.visibility = View.VISIBLE
            }
        }
    }

    private fun clearErrors() {
        errorEmptyView?.visibility = View.INVISIBLE
        errorWrongDataView?.visibility = View.INVISIBLE
    }

    override fun onDetach() {
        login = null
        super.onDetach()
    }

}