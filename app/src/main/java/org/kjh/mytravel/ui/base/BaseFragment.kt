package org.kjh.mytravel.ui.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.LayoutProfileInfoBinding

/**
 * MyTravel
 * Class: BaseFragment
 * Created by jonghyukkang on 2022/01/22.
 *
 * Description:
 */
abstract class BaseFragment<B: ViewDataBinding>(
    private val layoutId: Int
): Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setStatusBarColor()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribeUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun initView()
    abstract fun subscribeUi()

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun setStatusBarColor() {
        if (layoutId == R.layout.fragment_home
            || layoutId == R.layout.fragment_daylog_detail) {
            requireActivity().window.statusBarColor= Color.TRANSPARENT
        }
    }
}