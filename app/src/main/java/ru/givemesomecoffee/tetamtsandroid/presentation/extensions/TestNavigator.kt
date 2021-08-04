package ru.givemesomecoffee.tetamtsandroid.presentation.extensions

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.ArrayDeque

@Navigator.Name("fragment")
class TestNavigator(
    context: Context,
    fragmentManager: FragmentManager,
    containerId: Int,
    id: NavDestination,

    ) : Navigator<FragmentNavigator.Destination>() {
    var mContext = context
    var mFragmentManager = fragmentManager
    var mContainerId = containerId
    var startDestination = id.parent!!.startDestination.toString()
    private var mBackStack = ArrayDeque<Int>()
    private var bsState = ArrayDeque<Int>()

    private var testCurrent: MutableList<FragmentTransaction> = mutableListOf()
    private var testState: MutableList<FragmentTransaction> = mutableListOf()


    private var homeDestination = startDestination
//TODO: нужно сохранять транзакции в мапу/список типа каррент и стейт. и если каррент падает в 0 размер,
// то чистим бс менеджера, дергаем стейт и разворачиваем из него все транзакции о/ на смене узла делаем свап стейта с каррент чистим стак менеджера и опять разворачиваем все транзакции

    //TODO: проблема в том, что навконтроллер видит пустой стак и грустит при свапе. пофиг что там разворачиваю я или нет
    init {
        mBackStack.size + 1
        mBackStack.add(id.id)
        //  mFragmentManager.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
    }

    override fun popBackStack(): Boolean {
        Log.d("test123", "pop")
        Log.d("test1", mBackStack.toString())
        if (mBackStack.isEmpty()) {
            return false
        }
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring popBackStack() call: FragmentManager has already"
                        + " saved its state"
            )
            return false
        }
        mBackStack.removeLast()
        if (mBackStack.isEmpty() && startDestination != homeDestination && !bsState.isEmpty()) {
            Log.d("testtest", bsState.toString())
            mBackStack = bsState
            Log.d("testtest", bsState.size.toString())
            bsState = ArrayDeque<Int>()
            Log.d("testtest", mBackStack.toString())
            startDestination = homeDestination
            Log.d("test", mBackStack.last.toString())
            val ft = mFragmentManager.beginTransaction()

            mFragmentManager.popBackStack(
                mBackStack.first,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

            return true
        }

        mFragmentManager.popBackStack()


        return true
    }

    override fun createDestination(): FragmentNavigator.Destination {
        return FragmentNavigator.Destination(this)
    }


    private fun instantiateFragment(
        context: Context,
        fragmentManager: FragmentManager,
        className: String, args: Bundle?
    ): Fragment? {
        return fragmentManager.fragmentFactory.instantiate(
            context.classLoader, className
        )
    }

    override fun navigate(
        destination: FragmentNavigator.Destination, args: Bundle?,
        navOptions: NavOptions?, navigatorExtras: Extras?
    ): NavDestination? {
        Log.d("testNav", "42")
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already"
                        + " saved its state"
            )
            return null
        }
        Log.d("dest123", destination.parent!!.startDestination.toString())
        if (startDestination != destination.parent!!.startDestination.toString()) {
            /*  bsState = mBackStack
              *//*   val size = mBackStack.size
               mBackStack.clear()
               mBackStack.size-size*//*
            mBackStack = ArrayDeque<Int>()*/
            val temp = testState
            testState = testCurrent

            mFragmentManager.popBackStack(
                mBackStack.first,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            testCurrent = temp
            startDestination = destination.parent!!.startDestination.toString()
            if (testCurrent.size > 0) {
                for (i in testCurrent.size - 1..0) {
                    testCurrent[i].commit()
                }
            }


        }
        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }
        val frag = instantiateFragment(
            mContext, mFragmentManager,
            className, args
        )
        frag!!.arguments = args
        val ft = mFragmentManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
        ft.replace(mContainerId, frag)
        ft.setPrimaryNavigationFragment(frag)
        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && mBackStack.peekLast() == destId)
        val isAdded: Boolean = if (initialNavigation) {
            true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mFragmentManager.popBackStack(
                    generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
            }
            false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            true
        }
        if (navigatorExtras is FragmentNavigator.Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()

        Log.d("test1", mBackStack.toString())
        Log.d("test1", "tlen")
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    override fun onSaveState(): Bundle {
        val b = Bundle()
        val backStack = IntArray(mBackStack.size)
        var index = 0
        for (id in mBackStack) {
            backStack[index++] = id
        }
        b.putIntArray(KEY_BACK_STACK_IDS, backStack)
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        if (savedState != null) {
            val backStack = savedState.getIntArray(KEY_BACK_STACK_IDS)
            if (backStack != null) {
                mBackStack.clear()
                for (destId in backStack) {
                    mBackStack.add(destId)
                }
            }
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String? {
        return "$backStackIndex-$destId"
    }

    companion object {
        const val KEY_BACK_STACK_IDS = "androidx-nav-fragment:navigator:backStackIds"
        const val TAG = "FragmentNavigator"
    }
}