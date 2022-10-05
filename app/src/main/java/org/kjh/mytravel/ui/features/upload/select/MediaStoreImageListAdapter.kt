package org.kjh.mytravel.ui.features.upload.select

import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhSelectableMediaStoreImgItemBinding
import org.kjh.mytravel.model.MediaStoreImage

/**
 * MyTravel
 * Class: SelectPhotoListAdapter
 * Created by mac on 2022/01/19.
 *
 * Description:
 */

class MediaStoreImageListAdapter
    : ListAdapter<MediaStoreImage, MediaStoreImageListAdapter.SelectPhotoViewHolder>(MediaStoreImage.diffCallback){

    var tracker: SelectionTracker<Uri>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectPhotoViewHolder(
            VhSelectableMediaStoreImgItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SelectPhotoViewHolder, position: Int) {
        val item = getItem(position)
        tracker?.let {
            holder.bind(item, it.isSelected(item.contentUri))
        }
    }

    inner class SelectPhotoViewHolder(
        val binding: VhSelectableMediaStoreImgItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaStoreImage, isActivated: Boolean = false) {
            binding.mediaStoreImage = item

            binding.flSelectedCover.isActivated = isActivated
            binding.ivChecked.isVisible = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Uri> =
            object : ItemDetailsLookup.ItemDetails<Uri>() {
                override fun getPosition() = absoluteAdapterPosition
                override fun getSelectionKey() = getItem(absoluteAdapterPosition).contentUri
                override fun inSelectionHotspot(e: MotionEvent) = true
            }
    }
}
