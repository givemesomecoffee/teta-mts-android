package ru.givemesomecoffee.tetamtsandroid.presentation.navigation

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.givemesomecoffee.tetamtsandroid.Multistack
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.NavigationMainActivityContract
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragmentDirections

class NavigationPresenter(
    multistack: Multistack
) : NavigationMainActivityContract{
    var navController: NavController


    init{
        val navHostFragment = multistack.supportFragmentManager
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



}