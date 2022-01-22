package org.kjh.mytravel.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.GridLayoutItemDecoration
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.ui.PostSmallListAdapter

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val args: UserFragmentArgs by navArgs()
    private val viewModel: UserViewModel by viewModels { UserViewModelFactory(args.userEmail) }

    private val postSmallListAdapter by lazy {
        PostSmallListAdapter { item ->
            val action = NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userUiState.collect { uiState ->
                    binding.tbUserToolbar.title = uiState.userItem?.userEmail
//                    postSmallListAdapter.submitList(uiState.userItem?.postItems)
                }
            }
        }
        binding.tbUserToolbar.setupWithNavController(findNavController())

        binding.rvUserPostList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = postSmallListAdapter
            addItemDecoration(GridLayoutItemDecoration(requireContext()))
        }
    }
}