package org.kjh.mytravel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding       : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBottomNavViewWithNavigation()
    }

    // Set Up NavController with BottomNavigationView.
    private fun initBottomNavViewWithNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navController = navHostFragment.navController.apply {
            // whether Show or Hide BottomNavigationView.
            addOnDestinationChangedListener { _, _, arguments ->
                binding.bnvBottomNav.isVisible =
                    arguments?.getBoolean("showBnv", false) == true
            }
        }

        binding.bnvBottomNav.setupWithNavController(navController)
    }
}