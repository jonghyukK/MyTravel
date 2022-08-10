package org.kjh.mytravel.ui.features.daylog.contents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.VhDayLogDetailItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.features.daylog.DayLogDetailViewModel
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
    private val onClickBookmark: (Post) -> Unit,
    private val onClickShare   : (Post) -> Unit
): RecyclerView.Adapter<DayLogDetailItemAdapter.DayLogDetailItemViewHolder>() {

    private var currentPostItem: Post? = null
    private var isBookmarked   : Boolean = false

    fun setPostItem(post: Post) {
        currentPostItem = post
        notifyItemChanged(0)
    }

    fun setBookmarked(value: Boolean) {
        isBookmarked = value
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayLogDetailItemViewHolder {
        return DayLogDetailItemViewHolder(
            VhDayLogDetailItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickBookmark, onClickShare
        )
    }

    override fun onBindViewHolder(holder: DayLogDetailItemViewHolder, position: Int) {
        currentPostItem?.let { holder.bind(it, isBookmarked)}
    }

    override fun getItemCount() = 1

    class DayLogDetailItemViewHolder(
        private val binding        : VhDayLogDetailItemBinding,
        private val onClickBookmark: (Post) -> Unit,
        private val onClickShare   : (Post) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clPlaceInfoContainer.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    view.navigateTo(
                        NavGraphDirections.actionGlobalPlaceInfoWithDayLogFragment(post.placeName))
                }
            }

            binding.btnBookmark.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    onClickBookmark(post)
                }
            }

            binding.btnShare.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    onClickShare(post)
                }
            }
        }

        fun bind(postItem: Post, isBookmarked: Boolean) {
            binding.postItem = postItem
            binding.isBookmarked = isBookmarked
        }
    }
}
