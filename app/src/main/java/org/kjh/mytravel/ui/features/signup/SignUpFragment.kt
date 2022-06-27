package org.kjh.mytravel.ui.features.signup

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    override fun initView() {
        binding.fragment  = this
        binding.viewModel = viewModel
    }

    override fun subscribeUi() {
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                if (state.isRegistered) {
                    (parentFragment as NotLoginFragment).navigateHomeWhenSuccessLoginOrSignUp()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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
