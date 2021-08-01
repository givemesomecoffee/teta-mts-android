package ru.givemesomecoffee.tetamtsandroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.ProfileFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.navigation.NavigationPresenter

class Multistack : AppCompatActivity(), MoviesListFragmentClickListener,
    ProfileFragmentClickListener {
    private lateinit var bottomNavigationBar: BottomNavigationView
    private lateinit var navigation: NavigationPresenter

    //TODO: Clean ALL the mess
    //TODO: check style (requireView and view?
    //TODO: rename profile to account or vice versa
    //TODO: change logic of repository (should use saved state if there is, should make new variables only on init and refresh)
    //TODO: check number of pixels refreshed in bottom nav view :C
    private fun init() {
        bottomNavigationBar = findViewById(R.id.nav)
        setupMenuItemCustomView()
        bottomNavigationBar.itemIconTintList = null
        navigation = NavigationPresenter(this)
        navigation.setupBottomNavigation(bottomNavigationBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multistack)
        init()
    }

    override fun onMovieCardClicked(id: Int) {
        navigation.openMovieCard(id)
    }

    override fun profileOnBackPressed() {
        navigation.backToHomeTab()
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
        Log.d("test", firstItemView.isSelected.toString())
        firstItemView.addView(firstItemViewLayout)
        val lastItemView = bottomNavigationMenuView.getChildAt(1) as BottomNavigationItemView
        lastItemView.addView(lastItemViewLayout)
        firstItemViewLayout.isSelected = true
    }
}