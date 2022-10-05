package org.kjh.mytravel.ui.features.profile

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentNotLoginBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.login.LoginFragment
import org.kjh.mytravel.ui.features.signup.SignUpFragment

@AndroidEntryPoint
class NotLoginFragment
    : BaseFragment<FragmentNotLoginBinding>(R.layout.fragment_not_login) {

    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleNavigateAction()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun initView() {
        binding.fragment = this
    }

    override fun subscribeUi() {}

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }

    fun showSignUpPage() {
        SignUpFragment().show(childFragmentManager, SignUpFragment.TAG)
    }

    fun showLoginPage() {
        LoginFragment().show(childFragmentManager, LoginFragment.TAG)
    }

    fun handleNavigateAction() {
        if (checkIfPrevBackStackIsProfile()) {
            navigateToHome()
        } else {
            findNavController().popBackStack()
        }
    }

    private fun checkIfPrevBackStackIsProfile() =
        findNavController().previousBackStackEntry?.destination?.id == R.id.profileFragment

    private fun navigateToHome() {
        val startDestination = findNavController().graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setRestoreState(true)
            .setPopUpTo(R.id.profile, true)
            .build()

        findNavController().navigate(startDestination, null, navOptions)
    }
}