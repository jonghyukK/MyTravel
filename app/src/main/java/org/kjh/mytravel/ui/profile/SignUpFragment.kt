package org.kjh.mytravel.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsFragmentSignUpBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment

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
    private val parentViewModel: NotLoginViewModel by viewModels({ requireParentFragment() })

    companion object {
        const val TAG = "SignUpFragment"
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val bottomSheet = it.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        view?.post {
            val parent = view!!.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view!!.measuredHeight
            parent.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.pbLoading.isVisible = state.isLoading
                    binding.tilEmail.error      = state.emailError
                    binding.tilPw.error         = state.pwError
                    binding.tilNickName.error   = state.nickNameError
                    binding.tilEmail.isErrorEnabled    = !state.emailError.isNullOrEmpty()
                    binding.tilPw.isErrorEnabled       = !state.pwError.isNullOrEmpty()
                    binding.tilNickName.isErrorEnabled = !state.nickNameError.isNullOrEmpty()

                    if (state.isRegistered) {
                        parentViewModel.setLoginOrSignUpState()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.tieEmail.onFocusChangeListener    = null
        binding.tiePw.onFocusChangeListener       = null
        binding.tieNickName.onFocusChangeListener = null
    }
}
