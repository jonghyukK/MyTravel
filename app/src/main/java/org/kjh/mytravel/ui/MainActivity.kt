package org.kjh.mytravel.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding       : ActivityMainBinding
    private lateinit var navController : NavController

    @Inject
    lateinit var globalErrorHandler: GlobalErrorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
        subscribeGlobalErrorEvent()
    }

    private fun initView() {
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

    // (추후 각 화면별 에러 처리 필요).
    private fun subscribeGlobalErrorEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                globalErrorHandler.errorEvent.collect {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}