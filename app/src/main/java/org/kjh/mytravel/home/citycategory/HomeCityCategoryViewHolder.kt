package org.kjh.mytravel.home.citycategory

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.CityItem
import org.kjh.mytravel.databinding.ItemCityCategoryBinding

/**
 * MyTravel
 * Class: HomeCityCategoryViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class HomeCityCategoryViewHolder(
    val binding: ItemCityCategoryBinding,
    private val onClickCityCategory: (CityItem) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CityItem) {
        binding.cityItem = item
        binding.clHomeCityCategoriesContainer.setOnClickListener {
            onClickCityCategory(item)
        }
    }
}