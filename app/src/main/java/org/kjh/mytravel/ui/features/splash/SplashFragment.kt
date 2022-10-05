package org.kjh.mytravel.ui.features.splash

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentSplashBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.my.LoginState
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.utils.navigateTo

/**
 * MyTravel
 * Class: SplashFragment
 * Created by jonghyukkang on 2022/08/22.
 *
 * Description:
 */

// todo - Android 12 Splash Screen 적용해야 함 (현재 임시)
@AndroidEntryPoint
class SplashFragment: BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    override fun initView() {}

    override fun subscribeUi() {
        checkLoginState()
    }

    private fun checkLoginState() {
        myProfileViewModel.loginUiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { loginState ->
                if (loginState !is LoginState.Uninitialized) {
                    goToHomeFragment()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun goToHomeFragment() =
        navigateTo(SplashFragmentDirections.actionToHome())

}