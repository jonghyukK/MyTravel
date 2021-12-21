package org.kjh.mytravel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemCityBinding

/**
 * MyTravel
 * Class: CityListAdapter
 * Created by mac on 2021/12/22.
 *
 * Description:
 */
class CityListAdapter(
    private val cityList: List<CityItem>
): RecyclerView.Adapter<CityListAdapter.CityListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListViewHolder {
        return CityListViewHolder(
            ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount() = cityList.size

    class CityListViewHolder(
        private val binding: ItemCityBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cityItem: CityItem) {
            binding.cityItem = cityItem
        }
    }
}