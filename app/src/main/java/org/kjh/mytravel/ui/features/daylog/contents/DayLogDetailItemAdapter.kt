package org.kjh.mytravel.ui.features.daylog.contents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.VhDayLogDetailItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: ImageAdapter
 * Created by jonghyukkang on 2022/08/04.
 *
 * Description:
 */

class DayLogDetailItemAdapter(
): RecyclerView.Adapter<DayLogDetailItemAdapter.DayLogDetailItemViewHolder>() {

    private var currentPostItem: Post? = null

    fun setPostItem(post: Post) {
        currentPostItem = post
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayLogDetailItemViewHolder {
        return DayLogDetailItemViewHolder(
            VhDayLogDetailItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DayLogDetailItemViewHolder, position: Int) {
        currentPostItem?.let { holder.bind(it)}
    }

    override fun getItemCount() = 1

    class DayLogDetailItemViewHolder(
        private val binding : VhDayLogDetailItemBinding,
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clPlaceInfoContainer.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateTo(
                        NavGraphDirections.actionGlobalPlaceInfoWithDayLogFragment(post.placeName))
                }
            }
        }

        fun bind(postItem: Post) {
            binding.postItem = postItem
        }
    }
}
