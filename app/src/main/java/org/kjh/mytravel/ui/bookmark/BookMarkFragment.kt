package org.kjh.mytravel.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import org.kjh.mytravel.ui.MainViewModel
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentBookMarkBinding

class BookMarkFragment : Fragment() {

    private lateinit var binding: FragmentBookMarkBinding
    private val mainViewModel: MainViewModel by activityViewModels()

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

//            val backStackId = mainViewModel.tabBackStack[mainViewModel.tabBackStack.size - 2]
//            mainViewModel.deleteTabBackStack(mainViewModel.tabBackStack[mainViewModel.tabBackStack.size - 1])
            findNavController().navigate(R.id.home, null, navOptions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookMarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
        }



//        val appConfiguration = (requireActivity() as MainActivity).appBarConfiguration
//        binding.tbToolbar2.setupWithNavController(findNavController(), appConfiguration)

//        binding.rvCityList.apply {
//            layoutManager = GridLayoutManager(view.context, 2)
//            adapter = CityListAdapter(cityItemList, viewType = 1) { item ->
//                val action = BookMarkFragmentDirections.actionBookMarkFragmentToBookMarkDetailFragment()
//                findNavController().navigate(action)
//            }
//            addItemDecoration(GridLayoutItemDecoration(this.context))
//        }
    }
}