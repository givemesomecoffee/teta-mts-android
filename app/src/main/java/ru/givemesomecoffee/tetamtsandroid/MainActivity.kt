package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.givemesomecoffee.tetamtsandroid.interfaces.MovieDetailsClickListener
import ru.givemesomecoffee.tetamtsandroid.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presenter.NavigationPresenter
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.view.MovieDetailsFragment.Companion.MOVIE_DETAILS_TAG
import ru.givemesomecoffee.tetamtsandroid.view.MoviesListFragment
import ru.givemesomecoffee.tetamtsandroid.view.MoviesListFragment.Companion.MOVIE_LIST_TAG
import ru.givemesomecoffee.tetamtsandroid.view.ProfileFragment
import ru.givemesomecoffee.tetamtsandroid.view.ProfileFragment.Companion.PROFILE_TAG

class MainActivity : AppCompatActivity(), MoviesListFragmentClickListener,
    MovieDetailsClickListener, ProfileFragmentClickListener {

    var moviesListFragment: MoviesListFragment? = null
    var profileFragment: ProfileFragment? = null
    var movieDetailsFragment: MovieDetailsFragment? = null
    val rootViewId = R.id.main_container
    private lateinit var navigationPresenter: NavigationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            moviesListFragment = MoviesListFragment()
            moviesListFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(rootViewId, this, MOVIE_LIST_TAG)
                    .commit()
            }
        } else {
            moviesListFragment =
                supportFragmentManager.findFragmentByTag(MOVIE_LIST_TAG) as? MoviesListFragment
            movieDetailsFragment =
                supportFragmentManager.findFragmentByTag(MOVIE_DETAILS_TAG) as? MovieDetailsFragment
            profileFragment =
                supportFragmentManager.findFragmentByTag(PROFILE_TAG) as? ProfileFragment
        }
        navigationPresenter = NavigationPresenter(this, supportFragmentManager)
        navigationPresenter.setNavigationClickListeners()
    }

    override fun onMovieCardClicked(id: Int) {
        navigationPresenter.onMovieCardClicked(id)
    }

    override fun movieDetailsOnBackPressed() {
        navigationPresenter.moviesDetailsOnBackPressed()
    }

    override fun profileOnBackPressed() {
        navigationPresenter.profileOnBackPressed()
    }

}


