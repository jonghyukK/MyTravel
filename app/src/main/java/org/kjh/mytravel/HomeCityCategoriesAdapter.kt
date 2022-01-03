package org.kjh.mytravel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.ItemCityCategoryBinding

/**
 * MyTravel
 * Class: HoeCityCategoriesAdapter
 * Created by mac on 2021/12/31.
 *
 * Description:
 */
class HomeCityCategoriesAdapter(
    private val categoryList: List<CityItem>
): RecyclerView.Adapter<HomeCityCategoriesAdapter.HomeCityCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeCityCategoryViewHolder(
            ItemCityCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: HomeCityCategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size

    class HomeCityCategoryViewHolder(
        val binding: ItemCityCategoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CityItem) {
            binding.cityItem = item
        }
    }
}
