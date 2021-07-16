package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import ru.givemesomecoffee.tetamtsandroid.MovieDetailsFragment.Companion.MOVIE_DETAILS_TAG

const val CATEGORY = "category_key"

class MainActivity : AppCompatActivity(), MoviesListFragment.SomeFragmentClickListener,
    MovieDetailsFragment.MovieDetailsClickListener {
    private var category: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MoviesListFragment())
            .commit()

        findViewById<TabLayout>(R.id.nav_bottom).addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.main_container, MoviesListFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }


    override fun onChangeColorButtonClicked(id: Int) {
        val navView = findViewById<TabLayout>(R.id.nav_bottom)
        slideToBottom(navView)
        navView.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MovieDetailsFragment.newInstance(id), MOVIE_DETAILS_TAG)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }


    override fun onBackStack() {
        slideToTop(findViewById<TabLayout>(R.id.nav_bottom))
        slideToLeft(findViewById(R.id.movie_details_root))
        supportFragmentManager.popBackStack()
    }



    private fun slideToBottom(view: View) {
        val animate = TranslateAnimation(0F, 0F, 0F, view.height.toFloat())
        animate.duration = 500
        view.startAnimation(animate)
        view.visibility = View.GONE
    }

    private fun slideToTop(view: View) {
        view.visibility = View.VISIBLE
    }

    // To animate view slide out from right to left
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


