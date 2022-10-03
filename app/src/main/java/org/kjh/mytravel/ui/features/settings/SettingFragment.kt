package org.kjh.mytravel.ui.features.settings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentSettingBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.my.LoginState
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    override fun initView() {
        binding.myProfileViewModel = myProfileViewModel
        binding.tbSettingToolbar.setupWithNavController(findNavController())
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myProfileViewModel.loginUiState.collect {
                    if (it is LoginState.NotLoggedIn) {
                        navigateToHome()
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        val startDestination = findNavController().graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setRestoreState(true)
            .setPopUpTo(R.id.profile, true)
            .build()
        findNavController().navigate(startDestination, null, navOptions)
    }
}