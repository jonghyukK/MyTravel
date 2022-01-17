package org.kjh.mytravel.ui.home.citycategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemCityCategoryBinding
import org.kjh.mytravel.ui.uistate.CityCategoryItemUiState

/**
 * MyTravel
 * Class: HoeCityCategoriesAdapter
 * Created by mac on 2021/12/31.
 *
 * Description:
 */
class HomeCityCategoriesAdapter(
    private val cityCategoryItems   : List<CityCategoryItemUiState>,
    private val onClickCityCategory : (CityCategoryItemUiState) -> Unit
): RecyclerView.Adapter<HomeCityCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeCityCategoryViewHolder(
            ItemCityCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickCityCategory
        )

    override fun onBindViewHolder(holder: HomeCityCategoryViewHolder, position: Int) {
        holder.bind(cityCategoryItems[position])
    }

    override fun getItemCount() = cityCategoryItems.size
}
