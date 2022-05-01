package org.kjh.mytravel.ui.features.place.subcity

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceByCityNameItemBinding
import org.kjh.mytravel.model.Post

/**
 * MyTravel
 * Class: PlacesBySubCityPostViewHolder
 * Created by jonghyukkang on 2022/04/29.
 *
 * Description:
 */

class PlacesBySubCityPostViewHolder(
    private val binding: VhPlaceByCityNameItemBinding,
    private val onClickPostItem: (Post) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) {
        binding.postItem = item

        itemView.setOnClickListener {
            onClickPostItem(item)
        }
    }
}