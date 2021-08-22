package ru.givemesomecoffee.tetamtsandroid.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.givemesomecoffee.tetamtsandroid.R
import ru.givemesomecoffee.tetamtsandroid.RefreshDataService
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.Login
import ru.givemesomecoffee.tetamtsandroid.presentation.interfaces.MoviesListFragmentClickListener
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.Authorisation
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragmentDirections
import java.util.concurrent.TimeUnit

private const val USER_TOKEN = "user_token"
private const val PREF_FILE_NAME = "UserPref"

class MainActivity : AppCompatActivity(), MoviesListFragmentClickListener,
    Login {
    private val authorisationController = Authorisation()
    private var login: Int? = null
    private var mSettings: SharedPreferences? = null
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private lateinit var bottomNavigationBar: BottomNavigationView
    private lateinit var navController: NavController
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
        mSettings = EncryptedSharedPreferences.create(
            PREF_FILE_NAME,
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        lifecycleScope.launch {
            withContext(Dispatchers.IO) { login = checkLoginStatus() }
            init()

            //swapping login/profile fragments
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (login == null && destination.id == R.id.profileFragment) {
                    navController.popBackStack()
                    navController.navigate(R.id.action_global_loginFragment)
                }
            }
        }

    }

    override fun onMovieCardClicked(id: Int) {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(id)
        navController.navigate(action)
    }

    override fun homeOnBackPressed(category: Int?) {
        if (category == null) {
            finishAffinity()
        } else {
            navController.navigate(navController.graph.findStartDestination().id)
        }
    }

    override fun showProfile() {
        navController.popBackStack()
        navController.navigate(R.id.action_global_profileFragment)
    }

    override fun saveLogin(id: Int, token: String) {
        mSettings?.edit()?.putString(USER_TOKEN, token)?.apply()
        login = id
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                authorisationController.setNewToken(token, id)
            }
        }

    }

    override fun onRegisterComplete() {
        navController.popBackStack()
    }

    override fun getUserId(): Int? {
        return login
    }

    override fun exitLogin() {
        clearLoginData()
        lastItemView?.performClick()
    }

    private fun checkLoginStatus(): Int? {
        val token = mSettings?.getString(USER_TOKEN, null)
        return token?.let { authorisationController.getUserId(it) }
    }

    private fun clearLoginData() {
        mSettings?.edit()?.remove(USER_TOKEN)?.apply()
        login = null
        lifecycleScope.launch {
            withContext(Dispatchers.IO) { login?.let { authorisationController.deleteToken(it) } }
        }

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
        lastItemView?.addView(lastItemViewLayout)
        firstItemViewLayout.isSelected = true
    }

}


