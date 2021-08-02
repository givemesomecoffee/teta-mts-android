package ru.givemesomecoffee.tetamtsandroid.presentation.extensions

import android.content.Context
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.fragment.FragmentNavigator

class TestNavigator<D : NavDestination>(
    context: Context,
    fragmentManager: FragmentManager,
    containerId: Int
) : FragmentNavigator(
    context,
    fragmentManager, containerId
) {
    private var savedStatesNodes: MutableList<String> = mutableListOf()
    private var savedBs: MutableMap<String, MutableList<NavBackStackEntry>> = mutableMapOf()


}