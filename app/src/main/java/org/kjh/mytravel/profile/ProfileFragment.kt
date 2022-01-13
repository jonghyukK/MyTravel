package org.kjh.mytravel.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.kjh.mytravel.*
import org.kjh.mytravel.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbarWithNavigation()
        initMyProfile()

        binding.btnSignUp.setOnClickListener {
            SignUpFragment().show(childFragmentManager, SignUpFragment.TAG)
        }

        binding.btnLogin.setOnClickListener {
            LoginFragment().show(childFragmentManager, LoginFragment.TAG)
        }
    }

    private fun initToolbarWithNavigation() {
        val appConfig = (requireActivity() as MainActivity).appBarConfiguration
        binding.tbProfileToolbar.setupWithNavController(findNavController(), appConfig)
    }

    private fun initMyProfile() {
        binding.rvMyPostList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = PostSmallListAdapter(tempPostItemList) { item ->
                val action =
                    NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
                findNavController().navigate(action)
            }
            setHasFixedSize(true)
            addItemDecoration(GridLayoutItemDecoration(requireContext()))
        }
    }
}