package org.kjh.mytravel.ui.features.place.detail

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

class PlaceDayLogPostImageAdapter
    : RecyclerView.Adapter<PlaceDayLogPostImageAdapter.PlaceDayLogPostImageViewHolder>() {

    private val postImages = mutableListOf<String>()

    fun setItems(items: List<String>) {
        postImages.clear()
        postImages.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceDayLogPostImageViewHolder(
            VhRectImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceDayLogPostImageViewHolder, position: Int) {
        holder.bind(postImages[position])
    }

    override fun getItemCount() = postImages.size

    class PlaceDayLogPostImageViewHolder(
        val binding : VhRectImageBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.postImage = imageUrl
        }
    }
}