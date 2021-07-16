package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import ru.givemesomecoffee.tetamtsandroid.MovieDetailsFragment.Companion.MOVIE_DETAILS_TAG
import ru.givemesomecoffee.tetamtsandroid.MoviesListFragment.Companion.MOVIE_LIST_TAG

//const val CATEGORY = "category_key"

class MainActivity : AppCompatActivity(), MoviesListFragment.MoviesListFragmentClickListener,
    MovieDetailsFragment.MovieDetailsClickListener {
  //  private var category: Int = 0

    // TODO: check num of px refreshed in movie details
//TODO: backstack of profile fragment

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
                Log.d("test", "1")
            }
        } else {
            moviesListFragment =
                supportFragmentManager.findFragmentByTag(MOVIE_LIST_TAG) as? MoviesListFragment
            Log.d("test", supportFragmentManager.fragments.toString())
        }

        findViewById<TabLayout>(R.id.nav_bottom).addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                } else {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.main_container, ProfileFragment())
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
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack("test")
            .commit()
        Log.d("test2", supportFragmentManager.fragments.toString())
    }

    override fun onBackStack() {
       findViewById<TabLayout>(R.id.nav_bottom).visibility = View.VISIBLE
        slideToLeft(findViewById(R.id.movie_details_root))
        supportFragmentManager.popBackStack()
    }

    private fun slideToBottom(view: View) {
        val animate = TranslateAnimation(0F, 0F, 0F, view.height.toFloat())
        animate.duration = 500
        view.startAnimation(animate)
        view.visibility = View.GONE
    }

    private fun slideToLeft(view: View) {
        val animate = TranslateAnimation(0F, (-view.width).toFloat(), 0F, 0F)
        animate.duration = 500
        view.startAnimation(animate)
        supportFragmentManager.findFragmentByTag(MOVIE_DETAILS_TAG)?.let {
            supportFragmentManager.beginTransaction()
                .remove(it)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
    }
}


