package org.kjh.mytravel.ui.features.profile.signup

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
import org.kjh.mytravel.databinding.BsFragmentSignUpBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment
import org.kjh.mytravel.ui.features.profile.NotLoginFragment

/**
 * MyTravel
 * Class: SignUpFragment
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

@AndroidEntryPoint
class SignUpFragment
    : BaseBottomSheetDialogFragment<BsFragmentSignUpBinding>(R.layout.bs_fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()

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
                    binding.tilEmail.error          = state.emailError
                    binding.tilEmail.isErrorEnabled = !state.emailError.isNullOrEmpty()

                    if (state.isRegistered) {
                        (parentFragment as NotLoginFragment)
                            .navigateHomeWhenSuccessLoginOrSignUp()
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
        binding.tieEmail.onFocusChangeListener    = null
        binding.tiePw.onFocusChangeListener       = null
        binding.tieNickName.onFocusChangeListener = null
    }

    companion object {
        const val TAG = "SignUpFragment"
    }
}
