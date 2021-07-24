package ru.givemesomecoffee.tetamtsandroid.presenter

import android.view.View
import androidx.fragment.app.FragmentManager
import ru.givemesomecoffee.tetamtsandroid.MainActivity
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.view.ProfileFragment

class NavigationPresenter(
    private val root: MainActivity,
    private val supportFragmentManager: FragmentManager
) {
    private var homeNavView: View = root.findViewById(R.id.button_nav_home)
    private var accountNavView: View = root.findViewById(R.id.button_nav_account)
    private val homeIndicator: View = root.findViewById(R.id.home_active_indicator)
    private val accountIndicator: View = root.findViewById(R.id.account_active_indicator)
    private var accountSelected = false

    fun setNavigationClickListeners() {
        homeNavView.setOnClickListener {
            supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            setHomeActive()
        }
        accountNavView.setOnClickListener {
            if (!accountSelected) {
                root.profileFragment = ProfileFragment()
                root.profileFragment?.apply {
                    supportFragmentManager.beginTransaction()
                        .add(root.rootViewId, this, ProfileFragment.PROFILE_TAG)
                        .hide(root.moviesListFragment!!)
                        .addToBackStack(ProfileFragment.PROFILE_TAG)
                        .commit()
                }
                if (root.movieDetailsFragment?.isVisible == true) {
                    supportFragmentManager.beginTransaction()
                        .hide(root.movieDetailsFragment!!)
                        .commit()
                }

                setAccountActive()
            }
        }
    }

    private fun setAccountActive() {
        homeIndicator.visibility = View.INVISIBLE
        accountIndicator.visibility = View.VISIBLE
        accountSelected = true
    }

    private fun setHomeActive() {
        accountIndicator.visibility = View.INVISIBLE
        homeIndicator.visibility = View.VISIBLE
        accountSelected = false
    }

    private fun showPrevFragment() {
        val fragment =
            if (supportFragmentManager.backStackEntryCount == 1) root.moviesListFragment else root.movieDetailsFragment
        supportFragmentManager.beginTransaction()
            .show(fragment!!)
            .commit()
    }

    fun onMovieCardClicked(id: Int) {
        root.movieDetailsFragment = MovieDetailsFragment.newInstance(id)
        root.movieDetailsFragment?.apply {
            supportFragmentManager.beginTransaction()
                .add(root.rootViewId, this, MovieDetailsFragment.MOVIE_DETAILS_TAG)
                .hide(root.moviesListFragment!!)
                .addToBackStack(MovieDetailsFragment.MOVIE_DETAILS_TAG)
                .commit()
        }
    }

    fun moviesDetailsOnBackPressed() {
        setHomeActive()
        showPrevFragment()
        supportFragmentManager.popBackStack()

    }

    fun profileOnBackPressed() {
        setHomeActive()
        showPrevFragment()
        supportFragmentManager.popBackStack()

    }

}