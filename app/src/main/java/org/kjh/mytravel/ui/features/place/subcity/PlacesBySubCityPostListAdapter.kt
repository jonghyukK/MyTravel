package org.kjh.mytravel.ui.features.place.subcity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceByCityNameItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.navigateToDayLogDetail
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceListByCityNamePostListAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityPostListAdapter
    : RecyclerView.Adapter<PlacesBySubCityPostListAdapter.PlacesBySubCityPostViewHolder>() {

    private val postItems = mutableListOf<Post>()

    fun setPostItems(items: List<Post>) {
        postItems.clear()
        postItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlacesBySubCityPostViewHolder(
            VhPlaceByCityNameItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlacesBySubCityPostViewHolder, position: Int) {
        holder.bind(postItems[position])
    }

    override fun getItemCount() = postItems.size

    class PlacesBySubCityPostViewHolder(
        private val binding : VhPlaceByCityNameItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateToDayLogDetail(post.placeName, post.postId)
                }
            }
        }

        fun bind(item: Post) {
            binding.postItem = item
        }
    }
}