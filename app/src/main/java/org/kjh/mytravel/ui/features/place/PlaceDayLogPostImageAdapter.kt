package org.kjh.mytravel.ui.features.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageBinding

/**
 * MyTravel
 * Class: PlaceDayLogPostImageAdapter
 * Created by jonghyukkang on 2022/05/02.
 *
 * Description:
 */

class PlaceDayLogPostImageAdapter(
    private val images: List<String>,
    private val onClickImg: () -> Unit,
): RecyclerView.Adapter<PlaceDayLogPostImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceDayLogPostImageViewHolder(
            VhRectImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickImg
        )

    override fun onBindViewHolder(holder: PlaceDayLogPostImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size
}