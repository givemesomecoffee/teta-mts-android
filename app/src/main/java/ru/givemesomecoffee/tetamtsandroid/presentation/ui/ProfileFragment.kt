package ru.givemesomecoffee.tetamtsandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.ProfileFragmentClickListener

class ProfileFragment : Fragment() {
    private var profileFragmentClickListener: ProfileFragmentClickListener? = null

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

    override fun onDetach() {
        super.onDetach()
        profileFragmentClickListener = null
    }

}