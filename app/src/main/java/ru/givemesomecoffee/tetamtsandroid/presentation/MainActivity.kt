package ru.givemesomecoffee.tetamtsandroid.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.Login
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragmentDirections


class MainActivity : AppCompatActivity(), MoviesListFragmentClickListener,
    Login {
    private lateinit var bottomNavigationBar: BottomNavigationView
    private lateinit var navController: NavController
    private var mSettings: SharedPreferences? = null
    private var login: String? = null
    private var lastItemView: BottomNavigationItemView? = null

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
        mSettings = getSharedPreferences("test", Context.MODE_PRIVATE)
        login = checkLoginStatus()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("navigation", login.toString())
            Log.d("navigation", destination.label.toString())
            if (login == null && destination.id == R.id.profileFragment) {
                Log.d("navigation", "moving to profile")
                navController.popBackStack()
                navController.navigate(R.id.action_global_loginFragment)
            }
        }
    }

    override fun onMovieCardClicked(id: Int) {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(id)
        navController.navigate(action)
    }

    private fun checkLoginStatus(): String? {
        return mSettings!!.getString("KEY_INT", null)
    }

    private fun clearLoginData() {
        mSettings?.edit()?.putString("KEY_INT", null)?.apply()
        login = null
    }

    override fun exitLogin() {
        clearLoginData()
        lastItemView?.performClick()
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
        lastItemView = bottomNavigationMenuView.getChildAt(1) as BottomNavigationItemView
        lastItemView!!.addView(lastItemViewLayout)
        firstItemViewLayout.isSelected = true
    }

    override fun showProfile() {
        navController.popBackStack()
        navController.navigate(R.id.action_global_profileFragment)
    }

    override fun saveLogin(id: Int) {
        mSettings?.edit()?.putString("KEY_INT", id.toString())?.apply()
        login = id.toString()
    }
}

//TODO: добавить крипту сюда и токен в базу. на чек возвращать токен, по токену дергать в переменную логин айдишку. айдишку прокидывать в профиль фрагмент.
//TODO: вынести в отдельный класс логику авторизации(?) короче подумать как разгрузить активити.