package ru.givemesomecoffee.tetamtsandroid.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
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
    val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

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
      //  mSettings = getSharedPreferences("test", Context.MODE_PRIVATE)
        mSettings = EncryptedSharedPreferences.create(
            "test",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        login = checkLoginStatus()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (login == null && destination.id == R.id.profileFragment) {
                navController.popBackStack()
                navController.navigate(R.id.action_global_loginFragment)
            }
        }
    }

    override fun onMovieCardClicked(id: Int) {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(id)
        navController.navigate(action)
    }

    override fun homeOnBackPressed(category: Int) {
        if (category != 0) {
            navController.navigate(navController.graph.findStartDestination().id)
        } else {
            finishAffinity()
        }
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


