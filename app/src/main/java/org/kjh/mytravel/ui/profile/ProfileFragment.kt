package org.kjh.mytravel.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.kjh.mytravel.DataStoreManager
import org.kjh.mytravel.GridLayoutItemDecoration
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.PostSmallListAdapter
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var dataStore: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val navOptions =
                NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .setPopUpTo(
                        findNavController().graph.findStartDestination().id,
                        inclusive = false,
                        saveState = true
                    ).build()
            findNavController().navigate(R.id.home, null, navOptions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val isLoggedIn = dataStore.getMyEmail.first().isNotBlank()

                if (isLoggedIn) {
                    initMyProfile()
                } else {
                    findNavController().navigate(R.id.notLoginFragment)
                }
            }
        }
    }

    private fun initMyProfile() {
        binding.viewModel = viewModel

        initToolbarWithNavigation()

        binding.rvMyPostList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = PostSmallListAdapter { item ->
                val action =
                    NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
                findNavController().navigate(action)
            }
            setHasFixedSize(true)
            addItemDecoration(GridLayoutItemDecoration(requireContext()))
        }
    }

    private fun initToolbarWithNavigation() {
        val appConfig = (requireActivity() as MainActivity).appBarConfiguration

        binding.tbProfileToolbar.apply {
            setupWithNavController(findNavController(), appConfig)
            inflateMenu(R.menu.menu_write)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.write_post -> {
                        val action = ProfileFragmentDirections.actionProfileFragmentToSelectPhotoFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}