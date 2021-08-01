package ru.givemesomecoffee.tetamtsandroid.presentation.presenter

import androidx.fragment.app.FragmentManager
import ru.givemesomecoffee.tetamtsandroid.MainActivity
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.NavigationMainActivityContract
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragment
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.ProfileFragment

class NavigationPresenter(
    private val root: MainActivity,
    private val supportFragmentManager: FragmentManager
) : NavigationMainActivityContract{
    private var moviesListFragment: MoviesListFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var movieDetailsFragment: MovieDetailsFragment? = null
    private val rootViewId = R.id.main_container
    private var accountSelected = false

    private fun showPrevFragment() {
        val fragment =
            if (supportFragmentManager.backStackEntryCount == 1) moviesListFragment else movieDetailsFragment
        supportFragmentManager.beginTransaction()
            .show(fragment!!)
            .commit()
    }

    override fun init() {
        moviesListFragment = MoviesListFragment()
        moviesListFragment?.apply {
            supportFragmentManager.beginTransaction()
                .add(rootViewId, this, MoviesListFragment.MOVIE_LIST_TAG)
                .commit()
        }
    }

    override fun onHomeClicked() {
        supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        root.setHomeActive()
        accountSelected = false
    }

    override fun onAccountClicked() {
        if (!accountSelected) {
            profileFragment = ProfileFragment()
            profileFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(rootViewId, this, ProfileFragment.PROFILE_TAG)
                    .hide(moviesListFragment!!)
                    .addToBackStack(ProfileFragment.PROFILE_TAG)
                    .commit()
            }
            if (movieDetailsFragment?.isVisible == true) {
                supportFragmentManager.beginTransaction()
                    .hide(movieDetailsFragment!!)
                    .commit()
            }
            root.setAccountActive()
            accountSelected = true
        }
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
        movieDetailsFragment = MovieDetailsFragment.newInstance(id)
        movieDetailsFragment?.apply {
            supportFragmentManager.beginTransaction()
                .add(rootViewId, this, MovieDetailsFragment.MOVIE_DETAILS_TAG)
                .hide(moviesListFragment!!)
                .addToBackStack(MovieDetailsFragment.MOVIE_DETAILS_TAG)
                .commit()
        }
    }

    override fun onBackPressed() {
        root.setHomeActive()
        accountSelected = false
        showPrevFragment()
        supportFragmentManager.popBackStack()
    }
}