package org.kjh.mytravel

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import org.kjh.mytravel.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val appBarConfiguration = AppBarConfiguration(
        setOf(R.id.homeFragment, R.id.bookMarkFragment, R.id.profileFragment)
    )

    private val mainViewModel: MainViewModel by viewModels()

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBottomNavViewWithNavigation()
    }

    private fun initBottomNavViewWithNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bnvBottomNav.apply {
            setupWithNavController(navController)

//            mainViewModel.addTabBackStack(this.menu.getItem(0).itemId)
//
//            setOnItemSelectedListener { item ->
//
//                val navOptions: NavOptions = NavOptions.Builder()
//                    .setLaunchSingleTop(true)
//                    .setRestoreState(true)
//                    .setPopUpTo(
//                        navController.graph.findStartDestination().id,
//                        false,  // inclusive
//                        true
//                    ) // saveState
//                    .build()
//
//                navController.navigate(item.itemId, null, navOptions)

//                mainViewModel.addTabBackStack(item.itemId)
//                NavigationUI.onNavDestinationSelected(item, navController)
//                true
//            }
        }

        navController.addOnDestinationChangedListener { navController, destination, bundle ->



            binding.bnvBottomNav.visibility =
                if (destination.id != R.id.homeFragment
                    && destination.id != R.id.bookMarkFragment
                    && destination.id != R.id.profileFragment
                ) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
        }
    }
}