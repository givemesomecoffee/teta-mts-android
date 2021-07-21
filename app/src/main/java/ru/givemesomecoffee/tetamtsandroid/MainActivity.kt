package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment.Companion.MOVIE_DETAILS_TAG
import ru.givemesomecoffee.tetamtsandroid.view.MoviesListFragment
import ru.givemesomecoffee.tetamtsandroid.view.MoviesListFragment.Companion.MOVIE_LIST_TAG
import ru.givemesomecoffee.tetamtsandroid.view.ProfileFragment
import ru.givemesomecoffee.tetamtsandroid.view.ProfileFragment.Companion.PROFILE_TAG

class MainActivity : AppCompatActivity(), MoviesListFragment.MoviesListFragmentClickListener,
    MovieDetailsFragment.MovieDetailsClickListener, ProfileFragment.ProfileFragmentClickListener {

    private var moviesListFragment: MoviesListFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var movieDetailsFragment: MovieDetailsFragment? = null
    private lateinit var navigationTabs: TabLayout
    private val rootViewId = R.id.main_container
    private var rootDetailsId = R.id.movie_details_root
    private var rootProfileId = R.id.profile_root


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        navigationTabs = findViewById(R.id.nav_bottom)
        navigationTabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else {
                    profileFragment = ProfileFragment()
                    profileFragment?.apply {
                        supportFragmentManager.beginTransaction()
                            .add(rootViewId, this, PROFILE_TAG)
                            .hide(moviesListFragment!!)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack("profile")
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onMovieCardClicked(id: Int) {
        movieDetailsFragment = MovieDetailsFragment.newInstance(id)
        movieDetailsFragment?.apply {
            supportFragmentManager.beginTransaction()
                .add(rootViewId, this, MOVIE_DETAILS_TAG)
                .hide(moviesListFragment!!)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("details")
                .commit()
        }
    }

    override fun customOnBackPressed() {
        navigationTabs.visibility = View.VISIBLE
        slideToLeft(findViewById(rootDetailsId), movieDetailsFragment)
        supportFragmentManager.popBackStack()
    }

    override fun hideNavigation() {
        val animate = TranslateAnimation(0F, 0F, 0F, navigationTabs.height.toFloat())
        animate.duration = 500
        navigationTabs.startAnimation(animate)
        navigationTabs.visibility = View.GONE
    }

    private fun slideToLeft(view: View, fragment: Fragment?) {
        val animate = TranslateAnimation(0F, (-view.width).toFloat(), 0F, 0F)
        animate.duration = 500
        view.startAnimation(animate)
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .show(moviesListFragment!!)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
    }

    override fun profileOnBackPressed() {
        slideToLeft(
            findViewById(rootProfileId),
            profileFragment
        )
        navigationTabs.getTabAt(0)?.select()
    }
}


