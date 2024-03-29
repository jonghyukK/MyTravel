package org.kjh.mytravel.ui.features.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsFragmentLoginBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment
import org.kjh.mytravel.ui.features.profile.NotLoginFragment

@AndroidEntryPoint
class LoginFragment
    : BaseBottomSheetDialogFragment<BsFragmentLoginBinding>(R.layout.bs_fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        binding.fragment  = this
        binding.viewModel = viewModel
    }

    override fun subscribeUi() {
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                if (state is LoginUiState.Success) {
                    (parentFragment as NotLoginFragment).handleNavigateAction()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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