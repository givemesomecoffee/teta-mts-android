package ru.givemesomecoffee.tetamtsandroid

import android.opengl.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayout

const val CATEGORY = "category_key"

class MainActivity : AppCompatActivity(), MoviesListFragment.SomeFragmentClickListener {
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
                if (tab != null) {
                    onTabSelected(tab)
                }
            }
        })



    }


    override fun onChangeColorButtonClicked(id: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MovieDetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}


