package org.kjh.mytravel.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * MyTravel
 * Class: BaseBottomSheetDialogFragment
 * Created by jonghyukkang on 2022/01/22.
 *
 * Description:
 */

abstract class BaseBottomSheetDialogFragment<B: ViewDataBinding>(
    private val layoutId: Int
): BottomSheetDialogFragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}