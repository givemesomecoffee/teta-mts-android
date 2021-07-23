package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import ru.givemesomecoffee.tetamtsandroid.interfaces.MovieDetailsClickListener
import ru.givemesomecoffee.tetamtsandroid.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment.Companion.MOVIE_DETAILS_TAG
import ru.givemesomecoffee.tetamtsandroid.view.MoviesListFragment
import ru.givemesomecoffee.tetamtsandroid.view.MoviesListFragment.Companion.MOVIE_LIST_TAG
import ru.givemesomecoffee.tetamtsandroid.view.ProfileFragment
import ru.givemesomecoffee.tetamtsandroid.view.ProfileFragment.Companion.PROFILE_TAG

class MainActivity : AppCompatActivity(), MoviesListFragmentClickListener,
    MovieDetailsClickListener, ProfileFragmentClickListener {

    private var moviesListFragment: MoviesListFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var movieDetailsFragment: MovieDetailsFragment? = null
    private var homeNav: View? = null
    private var accountNav: View? = null
    private val rootViewId = R.id.main_container
    private lateinit var homeIndicator: View
    private lateinit var accountIndicator: View
    private var accountSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test", "0")
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            moviesListFragment = MoviesListFragment()
            moviesListFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(rootViewId, this, MOVIE_LIST_TAG)
                    .commit()
            }
        } else {
            moviesListFragment =
                supportFragmentManager.findFragmentByTag(MOVIE_LIST_TAG) as? MoviesListFragment
            movieDetailsFragment =
                supportFragmentManager.findFragmentByTag(MOVIE_DETAILS_TAG) as? MovieDetailsFragment
            profileFragment =
                supportFragmentManager.findFragmentByTag(PROFILE_TAG) as? ProfileFragment
        }
        accountNav = findViewById(R.id.button_nav_account)
        homeNav = findViewById(R.id.button_nav_home)
        homeIndicator = findViewById(R.id.home_active_indicator)
        accountIndicator = findViewById(R.id.account_active_indicator)
        accountSelected = false
        setNavigationClickListeners(homeNav!!, accountNav!!)
    }

    override fun onMovieCardClicked(id: Int) {
        movieDetailsFragment = MovieDetailsFragment.newInstance(id)
        movieDetailsFragment?.apply {
            supportFragmentManager.beginTransaction()
                .add(rootViewId, this, MOVIE_DETAILS_TAG)
                .hide(moviesListFragment!!)
                .addToBackStack(MOVIE_DETAILS_TAG)
                .commit()
        }
    }

    override fun moviesDetailsOnBackPressed() {
        setHomeActive()
        showPrevFragment()
        supportFragmentManager.popBackStack()

    }

    override fun profileOnBackPressed() {
        setHomeActive()
        showPrevFragment()
        supportFragmentManager.popBackStack()

    }

    private fun setNavigationClickListeners(homeNav: View, accountNav: View) {
        homeNav.setOnClickListener {
            supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            setHomeActive()
        }
        accountNav.setOnClickListener {
            if (!accountSelected) {
                profileFragment = ProfileFragment()
                profileFragment?.apply {
                    supportFragmentManager.beginTransaction()
                        .add(rootViewId, this, PROFILE_TAG)
                        .hide(moviesListFragment!!)
                        .addToBackStack(PROFILE_TAG)
                        .commit()
                }
                if (movieDetailsFragment?.isVisible == true) {
                    supportFragmentManager.beginTransaction()
                        .hide(movieDetailsFragment!!)
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
        Log.d("test",supportFragmentManager.backStackEntryCount.toString() )
        val fragment =
            if (supportFragmentManager.backStackEntryCount == 1) moviesListFragment else movieDetailsFragment
        supportFragmentManager.beginTransaction()
            .show(fragment!!)
            .commit()
    }
}


