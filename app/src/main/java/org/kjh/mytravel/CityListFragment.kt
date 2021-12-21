package org.kjh.mytravel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.kjh.mytravel.databinding.FragmentCityListBinding

class CityListFragment : Fragment() {
    private lateinit var binding: FragmentCityListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_city_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCityList.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = CityListAdapter(cityItemList)
        }
    }
}