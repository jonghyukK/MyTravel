package org.kjh.mytravel.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val appBarConfiguration = AppBarConfiguration(
        setOf(R.id.homeFragment, R.id.bookMarkFragment, R.id.profileFragment)
    )

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBottomNavViewWithNavigation()
    }

    private fun initBottomNavViewWithNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvBottomNav.visibility =
                if (destination.id != R.id.homeFragment
                    && destination.id != R.id.bookMarkFragment
                    && destination.id != R.id.profileFragment
                    && destination.id != R.id.notLoginFragment
                ) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
        }
    }
}