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
import ru.givemesomecoffee.tetamtsandroid.controller.details.MovieDetailsFragment.Companion.MOVIE_DETAILS_TAG
import ru.givemesomecoffee.tetamtsandroid.MoviesListFragment.Companion.MOVIE_LIST_TAG
import ru.givemesomecoffee.tetamtsandroid.controller.details.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.controller.profile.ProfileFragment
import ru.givemesomecoffee.tetamtsandroid.controller.profile.ProfileFragment.Companion.PROFILE_TAG

class MainActivity : AppCompatActivity(), MoviesListFragment.MoviesListFragmentClickListener,
    MovieDetailsFragment.MovieDetailsClickListener, ProfileFragment.ProfileFragmentClickListener {

    private var moviesListFragment: MoviesListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            moviesListFragment = MoviesListFragment()
            moviesListFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_container, this, MOVIE_LIST_TAG)
                    .commit()
            }
        } else {
            moviesListFragment =
                supportFragmentManager.findFragmentByTag(MOVIE_LIST_TAG) as? MoviesListFragment
            Log.d("test", supportFragmentManager.fragments.toString())
        }


        val navigationTabs = findViewById<TabLayout>(R.id.nav_bottom)
        navigationTabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    //TODO: slideToLeft()
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.main_container, ProfileFragment(), PROFILE_TAG)
                        .hide(moviesListFragment!!)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("profile")
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onMovieCardClicked(id: Int) {
        val navView = findViewById<TabLayout>(R.id.nav_bottom)
        slideToBottom(navView)
        navView.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MovieDetailsFragment.newInstance(id), MOVIE_DETAILS_TAG)
            .hide(moviesListFragment!!)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack("test")
            .commit()
        Log.d("test2", supportFragmentManager.fragments.toString())
    }

    override fun onBackStack() {


        findViewById<TabLayout>(R.id.nav_bottom).visibility = View.VISIBLE
        slideToLeft(findViewById(R.id.movie_details_root), supportFragmentManager.findFragmentByTag(MOVIE_DETAILS_TAG))
        supportFragmentManager.popBackStack()
    }

    private fun slideToBottom(view: View) {
        val animate = TranslateAnimation(0F, 0F, 0F, view.height.toFloat())
        animate.duration = 500
        view.startAnimation(animate)
        view.visibility = View.GONE
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

    override fun customOnBackPressed() {
        slideToLeft(findViewById(R.id.profile_root), supportFragmentManager.findFragmentByTag(PROFILE_TAG))
        findViewById<TabLayout>(R.id.nav_bottom).getTabAt(0)?.select()
    }
}


