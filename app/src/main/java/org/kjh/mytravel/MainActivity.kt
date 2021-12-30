package org.kjh.mytravel

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val appBarConfiguration = AppBarConfiguration(
        setOf(R.id.homeFragment, R.id.bookMarkFragment, R.id.profileFragment)
    )

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBottomNavViewWithNavigation()
    }

    private fun initBottomNavViewWithNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bnvBottomNav.apply {
            setupWithNavController(navController)

            mainViewModel.addTabBackStack(this.menu.getItem(0).itemId)

            setOnItemSelectedListener { item ->
                mainViewModel.addTabBackStack(item.itemId)
                NavigationUI.onNavDestinationSelected(item, navController)
                true
            }
        }
    }
}