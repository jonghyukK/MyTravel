package org.kjh.mytravel.ui.features.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentSettingBinding
import org.kjh.mytravel.ui.base.BaseFragment


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.tbSettingToolbar.setupWithNavController(findNavController())
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.loginInfoPreferencesFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { loginInfo ->
                if (!loginInfo.isLoggedIn) {
                    navigateHomeWhenSuccessLogOut()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateHomeWhenSuccessLogOut() {
        val startDestination = findNavController().graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()

        findNavController().navigate(startDestination, null, navOptions)
    }
}