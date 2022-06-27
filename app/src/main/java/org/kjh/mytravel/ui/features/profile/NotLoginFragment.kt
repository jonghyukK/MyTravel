package org.kjh.mytravel.ui.features.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentNotLoginBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.login.LoginFragment
import org.kjh.mytravel.ui.features.signup.SignUpFragment

class NotLoginFragment
    : BaseFragment<FragmentNotLoginBinding>(R.layout.fragment_not_login) {

    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
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

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun initView() {
        binding.fragment = this
    }

    override fun subscribeUi() {}

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

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }
}