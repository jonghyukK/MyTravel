package org.kjh.mytravel.home.citycategory

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemCityCategoryBinding
import org.kjh.mytravel.uistate.CityCategoryItemUiState

/**
 * MyTravel
 * Class: HomeCityCategoryViewHolder
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class HomeCityCategoryViewHolder(
    private val binding             : ItemCityCategoryBinding,
    private val onClickCityCategory : (CityCategoryItemUiState) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CityCategoryItemUiState) {
        binding.cityCategoryItemUiState = item
        binding.clHomeCityCategoriesContainer.setOnClickListener {
            onClickCityCategory(item)
        }
    }
}