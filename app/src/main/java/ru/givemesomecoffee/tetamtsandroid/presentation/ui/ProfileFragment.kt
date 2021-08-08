package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.ProfileViewModel
import ru.givemesomecoffee.tetamtsandroid.presentation.widget.adapter.CategoryFavouriteAdapter
import ru.givemesomecoffee.tetamtsandroid.utils.RecyclerItemDecoration

class ProfileFragment : Fragment() {
    private var profileFragmentClickListener: ProfileFragmentClickListener? = null
    private val viewModel: ProfileViewModel by viewModels()
    private var headerName: TextView? = null
    private var headerEmail: TextView? = null
    private var inputName: TextInputEditText? = null
    private var inputEmail: TextInputEditText? = null
    private var inputPassword: TextInputEditText? = null
    private var favouriteCategoriesListView: RecyclerView? = null

    private fun init() {
        headerName = view?.findViewById(R.id.profile_name)
        headerEmail = view?.findViewById(R.id.profile_email)
        inputName = view?.findViewById(R.id.profile_name_input_edit)
        inputEmail = view?.findViewById(R.id.profile_email_input_edit)
        inputPassword = view?.findViewById(R.id.profile_password_input_edit)
        favouriteCategoriesListView = requireView().findViewById(R.id.profile_favourite_list)
        favouriteCategoriesListView!!.addItemDecoration(RecyclerItemDecoration(6, 0, 20))

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProfileFragmentClickListener) {
            profileFragmentClickListener = context
        }
        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    profileFragmentClickListener?.profileOnBackPressed()

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.getUser(0)
        viewModel.data.observe(viewLifecycleOwner, Observer(::bindData))
    }



    override fun onDetach() {
        super.onDetach()
        profileFragmentClickListener = null
    }
    private fun bindData(list: List<UserWithFavourites>?) {
        val temp = list?.get(0)
        val user = temp?.user
        val categories = temp?.categories
        Log.d("room", user.toString())
        Log.d("room", categories.toString())
        headerName?.text = user?.name
        headerEmail?.text = user?.email
        inputName?.text = Editable.Factory.getInstance().newEditable(user?.name)
        inputEmail?.text = Editable.Factory.getInstance().newEditable(user?.email)
        inputPassword?.text = Editable.Factory.getInstance().newEditable(user?.password)
        favouriteCategoriesListView?.adapter = CategoryFavouriteAdapter(categories!!)
    }
}


