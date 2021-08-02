package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.NavigationMainActivityContract
import ru.givemesomecoffee.tetamtsandroid.presentation.navigation.NavigationPresenter

import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragmentDirections


class MainActivity1 : AppCompatActivity(), MoviesListFragmentClickListener {

    private lateinit var navController: NavController
    private lateinit var homeIndicator: View
    private lateinit var accountIndicator: View

    private fun init() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        homeIndicator = findViewById(R.id.home_active_indicator)
        accountIndicator = findViewById(R.id.account_active_indicator)
        val test: View = findViewById(R.id.button_nav_account)
        val home: View = findViewById(R.id.button_nav_home)
        val action1 = NavGraphDirections.actionGlobalProfileFragment()
        test.setOnClickListener {
            if (navController.currentDestination?.label.toString() != "ProfileFragment")
                navController.navigate(action1)
        }
        val action = NavGraphDirections.actionGlobalMoviesListFragment()
        home.setOnClickListener {
            navController.navigate(action)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("ds123", destination.label.toString())
            if (destination.label == "MovieDetailsFragment" || destination.label == "MoviesListFragment") {
                setHomeActive()
            } else {
                setAccountActive()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onMovieCardClicked(id: Int) {
        val action =
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(id = id)
        navController.navigate(action)
    }

    private fun setAccountActive() {
        homeIndicator.visibility = View.INVISIBLE
        accountIndicator.visibility = View.VISIBLE
    }

    private fun setHomeActive() {
        accountIndicator.visibility = View.INVISIBLE
        homeIndicator.visibility = View.VISIBLE
    }

}


