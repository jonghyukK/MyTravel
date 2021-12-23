package org.kjh.mytravel

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_city_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCityListHorizontal.apply {
            layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            adapter = CityListAdapter(cityItemList)
            addItemDecoration(LinearLayoutItemDecoration(this.context, top = 10, bottom = 10))
        }

        binding.rvCityList.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = CityListAdapter(cityItemList, viewType = 1)
            addItemDecoration(GridLayoutItemDecoration(this.context))
        }
    }
}