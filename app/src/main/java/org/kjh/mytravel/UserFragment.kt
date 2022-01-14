package org.kjh.mytravel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.databinding.VhPostSmallItemBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

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

        if (viewModel.userPageUiState.value == null) {
            viewModel.getUser(args.userEmail)
        }

        viewModel.userPageUiState.observe(viewLifecycleOwner, { uiState ->
            binding.tbUserToolbar.title = uiState.userItem.userEmail
            postSmallListAdapter.submitList(uiState.userItem.postItems)
        })

        binding.tbUserToolbar.setupWithNavController(findNavController())

        binding.rvUserPostList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = postSmallListAdapter
            addItemDecoration(GridLayoutItemDecoration(requireContext()))
        }
    }
}