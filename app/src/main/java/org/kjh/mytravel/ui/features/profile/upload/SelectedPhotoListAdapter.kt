package org.kjh.mytravel.ui.features.profile.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageWithCloseBtnBinding
import org.kjh.mytravel.model.MediaStoreImage

/**
 * MyTravel
 * Class: SelectedPhotoListAdapter
 * Created by mac on 2022/01/19.
 *
 * Description:
 */
class SelectedPhotoListAdapter(
    private val onDeleteImg: (MediaStoreImage) -> Unit
) : ListAdapter<MediaStoreImage, SelectedPhotoViewHolder>(MediaStoreImage.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectedPhotoViewHolder(
            VhRectImageWithCloseBtnBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onDeleteImg
        )

    override fun onBindViewHolder(holder: SelectedPhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SelectedPhotoViewHolder(
    val binding: VhRectImageWithCloseBtnBinding,
    private val onDeleteImg: (MediaStoreImage) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaStoreImage) {
        binding.item = item

        binding.ivDeleteImg.setOnClickListener {
            onDeleteImg(item)
        }
    }
}