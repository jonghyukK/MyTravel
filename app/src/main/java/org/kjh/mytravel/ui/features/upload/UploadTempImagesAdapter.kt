package org.kjh.mytravel.ui.features.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageBigBinding
import org.kjh.mytravel.model.MediaStoreImage

/**
 * MyTravel
 * Class: WritePostImagesAdapter
 * Created by jonghyukkang on 2022/01/23.
 *
 * Description:
 */

class UploadTempImagesAdapter
    : ListAdapter<MediaStoreImage, UploadTempImagesAdapter.TempImagesViewHolder>(MediaStoreImage.diffCallback){

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
        }
    }
}
