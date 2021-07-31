package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragmentDirections
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.ProfileFragmentDirections

class Multistack : AppCompatActivity(), MoviesListFragmentClickListener,
    ProfileFragmentClickListener {
    private lateinit var navController: NavController
    private lateinit var bottomNavigationBar: BottomNavigationView

    private fun init() {
        bottomNavigationBar = findViewById(R.id.nav)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.enableOnBackPressed(false)
        bottomNavigationBar.setupWithNavController(navController)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multistack)
        init()
    }


    override fun onMovieCardClicked(id: Int) {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(id)
        navController.navigate(action)
    }

    override fun profileOnBackPressed() {
        if (navController.backQueue.size > 1) {
            navController.popBackStack()
        }
        val builder = NavOptions.Builder().setLaunchSingleTop(true).setRestoreState(true)
        builder.setPopUpTo(
            navController.graph.findStartDestination().id,
            inclusive = false,
            saveState = false
        )
        val options = builder.build()

        navController.navigate(navController.graph.findStartDestination().id, null, options)

    }

    override fun testPressed() {
        val action = ProfileFragmentDirections.actionProfileFragmentToTest()
        navController.navigate(action)
    }
}