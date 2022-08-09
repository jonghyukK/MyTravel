package org.kjh.mytravel.ui.features.daylog.profiles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.VhDayLogUserProfileItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.features.daylog.SelectablePost
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceDetailUserProfileListAdapter
 * Created by jonghyukkang on 2022/06/28.
 *
 * Description:
 */

class DayLogDetailUserProfilesInnerAdapter(
    private val onClickProfile: (Post) -> Unit
) : ListAdapter<SelectablePost, DayLogDetailUserProfilesInnerAdapter.PlaceDetailUserProfileViewHolder>(
    SELECTABLE_POST_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceDetailUserProfileViewHolder {
        return PlaceDetailUserProfileViewHolder(
            VhDayLogUserProfileItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickProfile
        )
    }

    override fun onBindViewHolder(holder: PlaceDetailUserProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlaceDetailUserProfileViewHolder(
        val binding: VhDayLogUserProfileItemBinding,
        val onClickProfile: (Post) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick {
                binding.postItem?.let { post ->
                    onClickProfile(post.postItem)
                }
            }

            binding.tvNickName.onThrottleClick { view ->
                binding.postItem?.let { post ->
                    val action = NavGraphDirections.actionGlobalUserFragment(post.postItem.email)
                    view.navigateTo(action)
                }
            }
        }

        fun bind(postItem: SelectablePost) {
            binding.postItem = postItem
        }
    }

    companion object {
        private val SELECTABLE_POST_COMPARATOR = object: DiffUtil.ItemCallback<SelectablePost>() {
            override fun areItemsTheSame(
                oldItem: SelectablePost,
                newItem: SelectablePost
            ) = (oldItem.postItem.nickName == newItem.postItem.nickName) ||
                    (oldItem.isSelected == newItem.isSelected)

            override fun areContentsTheSame(
                oldItem: SelectablePost,
                newItem: SelectablePost
            ) = oldItem == newItem
        }
    }
}