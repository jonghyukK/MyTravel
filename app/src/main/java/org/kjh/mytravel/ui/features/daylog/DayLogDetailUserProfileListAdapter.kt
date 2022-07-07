package org.kjh.mytravel.ui.features.daylog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.VhPlaceDetailUserProfileItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PlaceDetailUserProfileListAdapter
 * Created by jonghyukkang on 2022/06/28.
 *
 * Description:
 */

class DayLogDetailUserProfileListAdapter(
    private val onClickProfile: (Post) -> Unit
) : RecyclerView.Adapter<DayLogDetailUserProfileListAdapter.PlaceDetailUserProfileViewHolder>() {

    private var currentSelectedUserIndex = -1
    private val userItems = mutableListOf<SelectablePost>()

    fun setUserItems(items: List<SelectablePost>) {
        userItems.clear()
        userItems.addAll(items)

        if (currentSelectedUserIndex == -1) {
            updateSelectedUserIndex()
            notifyDataSetChanged()
        } else {
            notifyItemChanged(currentSelectedUserIndex)
            updateSelectedUserIndex()
            notifyItemChanged(currentSelectedUserIndex)
        }
    }

    private fun updateSelectedUserIndex() {
        userItems.mapIndexed { index, selectablePost ->
            if (selectablePost.isSelected) {
                currentSelectedUserIndex = index
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceDetailUserProfileViewHolder(
            VhPlaceDetailUserProfileItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickProfile
        )

    override fun onBindViewHolder(holder: PlaceDetailUserProfileViewHolder, position: Int) {
        holder.bind(userItems[position])
    }

    override fun getItemCount() = userItems.size

    class PlaceDetailUserProfileViewHolder(
        val binding: VhPlaceDetailUserProfileItemBinding,
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

            if (postItem.isSelected) {
                binding.mlMotionContainer.transitionToState(R.id.end)
            } else {
                binding.mlMotionContainer.transitionToState(R.id.start)
            }
        }
    }
}

