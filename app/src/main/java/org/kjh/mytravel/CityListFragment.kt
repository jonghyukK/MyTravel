package org.kjh.mytravel

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.FragmentCityListBinding

class CityListFragment : Fragment() {
    private lateinit var binding: FragmentCityListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        binding.tbToolbar1.setupWithNavController(findNavController(), appConfiguration)

        binding.rvCityList.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = CityListAdapter(cityItemList, viewType = 1) { item ->
                val action = CityListFragmentDirections.actionCityListFragmentToCityDetailFragment(item.cityName)
                view.findNavController().navigate(action)
            }
            addItemDecoration(GridLayoutItemDecoration(this.context))
        }
    }
}