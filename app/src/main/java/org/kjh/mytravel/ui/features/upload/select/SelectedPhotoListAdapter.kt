package org.kjh.mytravel.ui.features.upload.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhSelectedMediaStoreImgItemBinding
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.ui.common.setOnThrottleClickListener

/**
 * MyTravel
 * Class: SelectedPhotoListAdapter
 * Created by mac on 2022/01/19.
 *
 * Description:
 */
class SelectedPhotoListAdapter(
    private val onDeleteImg: (MediaStoreImage) -> Unit
) : ListAdapter<MediaStoreImage, SelectedPhotoListAdapter.SelectedPhotoViewHolder>(MediaStoreImage.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectedPhotoViewHolder(
            VhSelectedMediaStoreImgItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onDeleteImg
        )

    override fun onBindViewHolder(holder: SelectedPhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SelectedPhotoViewHolder(
        private val binding: VhSelectedMediaStoreImgItemBinding,
        private val onDeleteImg: (MediaStoreImage) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivDeleteImg.setOnThrottleClickListener { view ->
                binding.item?.let { item ->
                    onDeleteImg(item)
                }
            }
        }

        fun bind(item: MediaStoreImage) {
            binding.item = item
        }
    }
}
