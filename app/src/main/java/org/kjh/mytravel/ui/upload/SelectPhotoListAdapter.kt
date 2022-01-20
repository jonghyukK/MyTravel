package org.kjh.mytravel.ui.upload

import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mytravel.databinding.VhRectImageSmallBinding

/**
 * MyTravel
 * Class: SelectPhotoListAdapter
 * Created by mac on 2022/01/19.
 *
 * Description:
 */

class SelectPhotoListAdapter :
    ListAdapter<MediaStoreImage, SelectPhotoListAdapter.SelectPhotoViewHolder>(MediaStoreImage.DiffCallback){

    init {
        setHasStableIds(true)
    }

    var tracker: SelectionTracker<Uri>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectPhotoViewHolder(
            VhRectImageSmallBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SelectPhotoViewHolder, position: Int) {
        val item = getItem(position)
        tracker?.let {
            holder.bind(item, it.isSelected(item.contentUri))
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    inner class SelectPhotoViewHolder(
        val binding: VhRectImageSmallBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MediaStoreImage, isActivated: Boolean = false) {
            binding.mediaStoreImage = item

            binding.flSelectedBg.isActivated = isActivated
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
