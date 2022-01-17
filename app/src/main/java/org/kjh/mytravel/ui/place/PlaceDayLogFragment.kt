package org.kjh.mytravel.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.ui.PostListAdapter
import org.kjh.mytravel.databinding.FragmentPlaceDayLogBinding


class PlaceDayLogFragment : Fragment() {
    private lateinit var binding: FragmentPlaceDayLogBinding
    private val viewModel: PlaceViewModel by viewModels({ requireParentFragment() })

    private val postListAdapter by lazy {
        PostListAdapter { item ->
            val action = NavGraphDirections.actionGlobalUserFragment(item.writer)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceDayLogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.placeUiState.collect { uiState ->
                    when (uiState) {
                        is PlaceUiState.Success -> postListAdapter.submitList(uiState.placeItems.postItems)
                        is PlaceUiState.Error -> Toast.makeText(requireContext(), "Place Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        initPostListByPlace()
    }

    private fun initPostListByPlace() {
        binding.rvPlaceDayLogList.apply {
            adapter = postListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.rvPlaceDayLogList.adapter == null) {
            binding.rvPlaceDayLogList.adapter = postListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPlaceDayLogList.adapter = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceDayLogFragment()
    }
}