package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragmentDirections

class MainActivity : AppCompatActivity(), MoviesListFragmentClickListener,
    ProfileFragmentClickListener {
    private lateinit var bottomNavigationBar: BottomNavigationView
    private lateinit var navController: NavController

    private fun init() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationBar = findViewById(R.id.nav)
        setupMenuItemCustomView()
        bottomNavigationBar.itemIconTintList = null
        bottomNavigationBar.setupWithNavController(navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    private fun setupMenuItemCustomView() {
        val firstItemViewLayout = LayoutInflater.from(this)
            .inflate(
                R.layout.first_nav_item,
                bottomNavigationBar, false
            )
        val lastItemViewLayout = LayoutInflater.from(this)
            .inflate(
                R.layout.last_nav_item_menu,
                bottomNavigationBar, false
            )
        val bottomNavigationMenuView: BottomNavigationMenuView =
            bottomNavigationBar.getChildAt(0) as BottomNavigationMenuView
        val firstItemView = bottomNavigationMenuView.getChildAt(0) as BottomNavigationItemView
        firstItemView.addView(firstItemViewLayout)
        val lastItemView = bottomNavigationMenuView.getChildAt(1) as BottomNavigationItemView
        lastItemView.addView(lastItemViewLayout)
        firstItemViewLayout.isSelected = true
    }
}