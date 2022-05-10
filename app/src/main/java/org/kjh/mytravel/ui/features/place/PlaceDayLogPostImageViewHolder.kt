package org.kjh.mytravel.ui.features.place

import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageBinding
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceDayLogPostImageViewHolder
 * Created by jonghyukkang on 2022/05/02.
 *
 * Description:
 */

class PlaceDayLogPostImageViewHolder(
    val binding   : VhRectImageBinding,
    val onClickImg: () -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(imgResource: String) {
        binding.postImage = imgResource

        itemView.onThrottleClick {
            onClickImg()
        }
    }
}