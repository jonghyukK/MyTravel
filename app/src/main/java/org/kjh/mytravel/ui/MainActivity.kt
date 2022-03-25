package org.kjh.mytravel.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.ActivityMainBinding

@AndroidEntryPoint
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

        binding.tbMainToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bnvBottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.tbMainToolbar.visibility =
                if (destination.id == R.id.homeFragment
                    || destination.id == R.id.placePagerFragment
                    || destination.id == R.id.profileFragment
                    || destination.id == R.id.selectPhotoFragment
                    || destination.id == R.id.writePostFragment
                    || destination.id == R.id.userFragment
                    || destination.id == R.id.notLoginFragment
                ) View.GONE else View.VISIBLE

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