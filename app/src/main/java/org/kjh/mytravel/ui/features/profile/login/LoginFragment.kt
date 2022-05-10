package org.kjh.mytravel.ui.features.profile.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsFragmentLoginBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment
import org.kjh.mytravel.ui.features.profile.NotLoginFragment

@AndroidEntryPoint
class LoginFragment
    : BaseBottomSheetDialogFragment<BsFragmentLoginBinding>(R.layout.bs_fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment  = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.isLoggedIn) {
                        (parentFragment as NotLoginFragment).navigateHomeWhenSuccessLoginOrSignUp()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeFocusChangeListener()
    }

    private fun removeFocusChangeListener() {
        binding.tieEmail.onFocusChangeListener = null
        binding.tiePw.onFocusChangeListener    = null
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}