package org.kjh.mytravel

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kjh.mytravel.databinding.BsFragmentSignUpBinding

/**
 * MyTravel
 * Class: SignUpFragment
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

class SignUpFragment: BottomSheetDialogFragment() {

    private lateinit var binding: BsFragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsFragmentSignUpBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.viewModel = viewModel

        viewModel.emailErrorState.observe(viewLifecycleOwner, {
            binding.tilEmail.error = it
        })

        viewModel.pwErrorState.observe(viewLifecycleOwner, {
            binding.tilPw.error = it
        })

        viewModel.nickNameErrorState.observe(viewLifecycleOwner, {
            binding.tilNickName.error = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.tieEmail.onFocusChangeListener = null
        binding.tiePw.onFocusChangeListener = null
        binding.tieNickName.onFocusChangeListener = null
    }
}
