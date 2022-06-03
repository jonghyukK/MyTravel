package org.kjh.mytravel.ui.features.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageBigBinding
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.utils.dpToPx

/**
 * MyTravel
 * Class: WritePostImagesAdapter
 * Created by jonghyukkang on 2022/01/23.
 *
 * Description:
 */

class WritePostImagesAdapter
    : ListAdapter<MediaStoreImage, WritePostImagesAdapter.TempImagesViewHolder>(MediaStoreImage.diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TempImagesViewHolder(
            VhRectImageBigBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TempImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TempImagesViewHolder(
        val binding: VhRectImageBigBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaStoreImage) {
            binding.item = item

            val displayWidth: Int = itemView.resources.displayMetrics.widthPixels
            binding.clRectImgContainer.layoutParams.width =
                displayWidth - 16.dpToPx(itemView.resources.displayMetrics) * 4
        }
    }
}
