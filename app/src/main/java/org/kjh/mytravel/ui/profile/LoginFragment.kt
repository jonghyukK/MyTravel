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
import org.kjh.mytravel.databinding.BsFragmentLoginBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment

@AndroidEntryPoint
class LoginFragment
    : BaseBottomSheetDialogFragment<BsFragmentLoginBinding>(R.layout.bs_fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private val parentViewModel: NotLoginViewModel by viewModels({ requireParentFragment() })

    companion object {
        const val TAG = "LoginFragment"
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
        binding.fragment        = this
        binding.viewModel       = viewModel
        binding.parentViewModel = parentViewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.pbLoading.isVisible = state.isLoading
                    binding.tilEmail.error      = state.emailError
                    binding.tilPw.error         = state.pwError
                    binding.tvLoginError.text   = state.loginError
                    binding.tvLoginError.isVisible  = state.loginError != null
                    binding.tilEmail.isErrorEnabled = !state.emailError.isNullOrEmpty()
                    binding.tilPw.isErrorEnabled    = !state.pwError.isNullOrEmpty()

                    if (state.isLoggedIn) {
                        parentViewModel.setLoginOrSignUpState()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.tieEmail.onFocusChangeListener = null
        binding.tiePw.onFocusChangeListener    = null
    }
}