package ru.givemesomecoffee.tetamtsandroid.presentation.navigation

import androidx.navigation.NavController

import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.givemesomecoffee.tetamtsandroid.MainActivity
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.NavigationMainActivityContract
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragmentDirections

class NavigationPresenter(
    mainActivity: MainActivity
) : NavigationMainActivityContract{
    lateinit var navController: NavController
    override fun openMovieCard(id: Int) {
        TODO("Not yet implemented")
    }

    override fun backToHomeTab() {
        TODO("Not yet implemented")
    }

/*

    init{
        val navHostFragment = mainActivity.supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.enableOnBackPressed(false)
    }

    fun setupBottomNavigation(view: BottomNavigationView){
        view.setupWithNavController(navController)
    }

    override fun openMovieCard(id: Int) {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(id)
        navController.navigate(action)
    }

    override fun backToHomeTab() {
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

*/


}