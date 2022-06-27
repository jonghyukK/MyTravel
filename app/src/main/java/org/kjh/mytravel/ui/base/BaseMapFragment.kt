package org.kjh.mytravel.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

/**
 * MyTravel
 * Class: BaseFragment
 * Created by jonghyukkang on 2022/01/22.
 *
 * Description:
 */
abstract class BaseMapFragment<B: ViewDataBinding>(
    private val layoutId: Int
): Fragment(), OnMapReadyCallback {

    lateinit var naverMap: NaverMap
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    abstract fun initView()

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        subscribeUi()
    }

    abstract fun subscribeUi()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}