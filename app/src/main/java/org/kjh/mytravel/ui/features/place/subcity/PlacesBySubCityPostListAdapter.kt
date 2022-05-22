package org.kjh.mytravel.ui.features.place.subcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceByCityNameItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceListByCityNamePostListAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityPostListAdapter(
    private val onClickPostItem: (Post) -> Unit
) : ListAdapter<Post, PlacesBySubCityPostListAdapter.PlacesBySubCityPostViewHolder>(Post.diffCallback) {

    class PlacesBySubCityPostViewHolder(
        private val binding        : VhPlaceByCityNameItemBinding,
        private val onClickPostItem: (Post) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.postItem = item

            itemView.onThrottleClick {
                onClickPostItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlacesBySubCityPostViewHolder(
            VhPlaceByCityNameItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPostItem
        )

    override fun onBindViewHolder(holder: PlacesBySubCityPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}