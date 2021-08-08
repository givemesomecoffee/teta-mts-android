package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.UserWithFavourites
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
    private var profileFragmentClickListener: ProfileFragmentClickListener? = null
    private val viewModel: ProfileViewModel by viewModels()

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
        viewModel.getUser(0)
        viewModel.data.observe(viewLifecycleOwner, Observer(::bindData))
    }
    override fun onDetach() {
        super.onDetach()
        profileFragmentClickListener = null
    }
    fun bindData(list: List<UserWithFavourites>?) {
        val temp = list?.get(0)
        val user = temp?.user
        val categories = temp?.categories
        Log.d("room", user.toString())
        Log.d("room", categories.toString())
    }
}


