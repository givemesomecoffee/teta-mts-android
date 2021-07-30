package ru.givemesomecoffee.tetamtsandroid.presentation.presenter

import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import ru.givemesomecoffee.tetamtsandroid.MainActivity
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.NavigationMainActivityContract
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragment
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.ProfileFragment
//import ru.givemesomecoffee.tetamtsandroid.presentation.ui.ProfileFragmentDirections

class NavigationPresenter(
    private val root: MainActivity,
    private val supportFragmentManager: FragmentManager
) : NavigationMainActivityContract{
    private var moviesListFragment: MoviesListFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var movieDetailsFragment: MovieDetailsFragment? = null
    private var accountSelected = false

    private fun showPrevFragment() {
        val fragment =
            if (supportFragmentManager.backStackEntryCount == 1) moviesListFragment else movieDetailsFragment
        supportFragmentManager.beginTransaction()
            .show(fragment!!)
            .commit()
    }

    override fun init() {
    }

    override fun onHomeClicked() {
        supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        accountSelected = false
    }

    override fun onAccountClicked() {
    }

    override fun recoverFragments() {
        moviesListFragment =
            supportFragmentManager.findFragmentByTag(MoviesListFragment.MOVIE_LIST_TAG) as? MoviesListFragment
        movieDetailsFragment =
            supportFragmentManager.findFragmentByTag(MovieDetailsFragment.MOVIE_DETAILS_TAG) as? MovieDetailsFragment
        profileFragment =
            supportFragmentManager.findFragmentByTag(ProfileFragment.PROFILE_TAG) as? ProfileFragment
    }

    override fun onMovieCardClicked(id: Int) {
    }

}