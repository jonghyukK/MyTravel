package org.kjh.mytravel.ui.features.profile

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentNotLoginBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.login.LoginFragment
import org.kjh.mytravel.ui.features.profile.signup.SignUpFragment
import org.kjh.mytravel.utils.onThrottleClick

class NotLoginFragment
    : BaseFragment<FragmentNotLoginBinding>(R.layout.fragment_not_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val navOptions =
                NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .setPopUpTo(
                        findNavController().graph.findStartDestination().id,
                        inclusive = false,
                        saveState = true
                    ).build()
            findNavController().navigate(R.id.home, null, navOptions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
    }

    fun navigateHomeWhenSuccessLoginOrSignUp() {
        val navController = findNavController()
        val startDestination = navController.graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()

        navController.navigate(startDestination, null, navOptions)
    }

    fun showSignUpPage() {
        SignUpFragment().show(childFragmentManager, SignUpFragment.TAG)
    }

    fun showLoginPage() {
        LoginFragment().show(childFragmentManager, LoginFragment.TAG)
    }
}