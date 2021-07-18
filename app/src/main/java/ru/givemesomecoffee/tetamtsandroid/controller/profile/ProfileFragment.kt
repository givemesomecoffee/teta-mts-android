package ru.givemesomecoffee.tetamtsandroid.controller.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.givemesomecoffee.tetamtsandroid.R

class ProfileFragment : Fragment() {
    private var profileFragmentClickListener: ProfileFragmentClickListener? = null

    companion object {
        const val PROFILE_TAG = "Profile"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProfileFragmentClickListener) {
            profileFragmentClickListener = context
        }
        val callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    profileFragmentClickListener?.customOnBackPressed()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        profileFragmentClickListener = null

    }

    interface ProfileFragmentClickListener {
        fun customOnBackPressed()
    }

}