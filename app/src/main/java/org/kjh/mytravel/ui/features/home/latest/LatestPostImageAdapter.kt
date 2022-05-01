package org.kjh.mytravel.ui.features.home.latest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageBinding

/**
 * MyTravel
 * Class: LatestPostImageListAdapter
 * Created by jonghyukkang on 2022/05/02.
 *
 * Description:
 */

class LatestPostImageAdapter(
    private val images    : List<String>,
    private val onClickImg: () -> Unit,
): RecyclerView.Adapter<LatestPostImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LatestPostImageViewHolder(
            VhRectImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickImg
        )

    override fun onBindViewHolder(holder: LatestPostImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size
}