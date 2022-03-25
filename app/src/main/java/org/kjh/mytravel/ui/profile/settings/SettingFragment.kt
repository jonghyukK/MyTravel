package org.kjh.mytravel.ui.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentSettingBinding
import org.kjh.mytravel.ui.base.BaseFragment


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickEvents()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginInfoPreferencesFlow.collect {
                    if (!it.isLoggedIn) {
                        Logger.e("Success Logout!")
                        navigateHomeWhenSuccessLogOut()
                    }
                }
            }
        }
    }

    private fun navigateHomeWhenSuccessLogOut() {
        val startDestination = findNavController().graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()

        findNavController().navigate(startDestination, null, navOptions)
    }

    private fun initClickEvents() {
        binding.tvLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}