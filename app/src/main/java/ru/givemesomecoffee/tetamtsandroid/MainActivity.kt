package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MovieDetailsClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.NavigationMainActivityContract
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.presenter.NavigationPresenter

class MainActivity : AppCompatActivity(), MoviesListFragmentClickListener,
    MovieDetailsClickListener, ProfileFragmentClickListener, NavigationMainActivityContract.View {

    private val navigationClickListeners: NavigationMainActivityContract =
        NavigationPresenter(this, supportFragmentManager)
    private lateinit var homeNavView: View
    private lateinit var accountNavView: View
    private lateinit var homeIndicator: View
    private lateinit var accountIndicator: View

    private fun init() {
        homeNavView = findViewById(R.id.button_nav_home)
        accountNavView = findViewById(R.id.button_nav_account)
        homeIndicator = findViewById(R.id.home_active_indicator)
        accountIndicator = findViewById(R.id.account_active_indicator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (savedInstanceState == null) {
            navigationClickListeners.init()
        } else {
            navigationClickListeners.recoverFragments()
        }
        homeNavView.setOnClickListener { navigationClickListeners.onHomeClicked() }
        accountNavView.setOnClickListener { navigationClickListeners.onAccountClicked() }
    }

    override fun onMovieCardClicked(id: Int) {
        navigationClickListeners.onMovieCardClicked(id)
    }

    override fun movieDetailsOnBackPressed() {
        navigationClickListeners.onBackPressed()
    }

    override fun profileOnBackPressed() {
        navigationClickListeners.onBackPressed()
    }

    override fun setAccountActive() {
        homeIndicator.visibility = View.INVISIBLE
        accountIndicator.visibility = View.VISIBLE
    }

    override fun setHomeActive() {
        accountIndicator.visibility = View.INVISIBLE
        homeIndicator.visibility = View.VISIBLE
    }

}


