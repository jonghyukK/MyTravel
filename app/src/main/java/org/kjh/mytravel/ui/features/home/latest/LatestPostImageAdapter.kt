package org.kjh.mytravel.ui.features.home.latest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageBinding
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: LatestPostImageListAdapter
 * Created by jonghyukkang on 2022/05/02.
 *
 * Description:
 */

class LatestPostImageAdapter(
    private val onClickImg: () -> Unit,
): RecyclerView.Adapter<LatestPostImageAdapter.LatestPostImageViewHolder>() {

    private val postImages = mutableListOf<String>()

    fun setItems(items: List<String>) {
        postImages.clear()
        postImages.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LatestPostImageViewHolder(
            VhRectImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickImg
        )

    override fun onBindViewHolder(holder: LatestPostImageViewHolder, position: Int) {
        holder.bind(postImages[position])
    }

    override fun getItemCount() = postImages.size

    class LatestPostImageViewHolder(
        val binding   : VhRectImageBinding,
        val onClickImg: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { onClickImg() }
        }

        fun bind(imgResource: String) {
            binding.postImage = imgResource
        }
    }
}