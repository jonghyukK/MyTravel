package org.kjh.mytravel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemCityBinding
import org.kjh.mytravel.databinding.ItemCityHorizontalBinding

/**
 * MyTravel
 * Class: CityListAdapter
 * Created by mac on 2021/12/22.
 *
 * Description:
 */
class CityListAdapter(
    private val cityList: List<CityItem>,
    private val viewType: Int = 0,
    private val onClickItem: (CityItem) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (viewType == 0) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> CityListHorizontalViewHolder(
                ItemCityHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> CityListViewHolder(
                ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickItem
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CityListViewHolder -> holder.bind(cityList[position])
            is CityListHorizontalViewHolder -> holder.bind(cityList[position])
        }
    }

    override fun getItemCount() = cityList.size

    class CityListViewHolder(
        private val binding: ItemCityBinding,
        private val onClickItem: (CityItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cityItem: CityItem) {
            binding.cityItem = cityItem
            binding.clCityItemContainer.setOnClickListener {
                onClickItem(cityItem)
            }
        }
    }

    class CityListHorizontalViewHolder(
        private val binding: ItemCityHorizontalBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(cityItem: CityItem) {
            binding.cityItem = cityItem
        }
    }

}