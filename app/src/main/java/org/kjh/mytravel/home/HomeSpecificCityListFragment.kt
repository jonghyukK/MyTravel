package org.kjh.mytravel.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.FragmentHomeSpecificCityListBinding
import org.kjh.mytravel.databinding.ItemCityListBinding
import org.kjh.mytravel.uistate.PlaceItemUiState
import org.kjh.mytravel.uistate.tempPlaceItemList

private const val ARG_PARAM1 = "param1"

class HomeSpecificCityListFragment : Fragment() {

    private var selectedCityName: String? = null
    private lateinit var binding: FragmentHomeSpecificCityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedCityName = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeSpecificCityListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListByCity()
    }

    private fun initListByCity() {
        val initItems = tempPlaceItemList.filter { it.cityName == selectedCityName }

        binding.rvSpecificCityList.apply {
            adapter = HomeSpecificCityListAdapter()
            setHasFixedSize(true)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            HomeSpecificCityListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}


class HomeSpecificCityListAdapter
    : ListAdapter<PlaceItemUiState, HomeSpecificCityListAdapter.HomeSpecificCityListViewHolder>(PlaceItemUiState.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSpecificCityListViewHolder {
        return HomeSpecificCityListViewHolder(
            ItemCityListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeSpecificCityListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HomeSpecificCityListViewHolder(
        val binding: ItemCityListBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceItemUiState) {
            binding.placeItemUiState = item
        }
    }
}