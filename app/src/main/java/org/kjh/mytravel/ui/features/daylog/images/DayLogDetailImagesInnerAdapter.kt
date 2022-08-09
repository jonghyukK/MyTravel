package org.kjh.mytravel.ui.features.daylog.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhDayLogImageItemBinding

/**
 * MyTravel
 * Class: PlaceDetailImageListAdapter
 * Created by jonghyukkang on 2022/06/28.
 *
 * Description:
 */

class DayLogDetailImagesInnerAdapter
    : ListAdapter<String, DayLogDetailImagesInnerAdapter.PlaceDetailImageViewHolder>(
    IMAGES_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceDetailImageViewHolder(
            VhDayLogImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceDetailImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlaceDetailImageViewHolder(
        val binding: VhDayLogImageItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.imageUrl = imageUrl
        }
    }

    companion object {
        private val IMAGES_COMPARATOR = object: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }
}