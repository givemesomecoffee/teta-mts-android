package ru.givemesomecoffee.tetamtsandroid

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
    }

    override fun onStart() {
        super.onStart()
        switchLayouts()
    }

    private fun switchLayouts(){
        val config = resources.configuration
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_movie_details_h)
        } else {
            setContentView(R.layout.activity_movie_details)
        }
    }
}