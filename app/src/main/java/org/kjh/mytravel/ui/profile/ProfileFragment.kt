package org.kjh.mytravel.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.kjh.mytravel.*
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.ui.LoginFragment
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.PostSmallListAdapter
import org.kjh.mytravel.ui.SignUpFragment


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

        MyTravelApplication.getInstance().getDataStore().isLogin
            .asLiveData().observe(viewLifecycleOwner, { logIn ->
                Log.e("isLogin", "login State : $logIn")
                if (!logIn) {
                    findNavController().navigate(R.id.notLoginFragment)
                } else {
                    initMyProfile()
                }
            })
    }

    private fun initMyProfile() {
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
        binding.tbProfileToolbar.setupWithNavController(findNavController(), appConfig)
    }
}