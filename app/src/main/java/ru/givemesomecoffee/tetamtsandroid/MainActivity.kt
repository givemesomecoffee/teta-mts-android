package ru.givemesomecoffee.tetamtsandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

const val CATEGORY = "category_key"

class MainActivity : AppCompatActivity() {
    private var category: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MovieDetailsFragment())
            .commit()

        findViewById<View>(R.id.button_nav_home).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesListFragment())
                .addToBackStack(null)
                .commit()
        }


    }
}


