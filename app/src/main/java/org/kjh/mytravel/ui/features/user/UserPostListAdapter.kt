package org.kjh.mytravel.ui.features.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PostSmallListAdapter
 * Created by mac on 2022/01/11.
 *
 * Description:
 */
class UserPostListAdapter(
    private val onClickPost    : (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): ListAdapter<Post, UserPostListAdapter.UserPostItemViewHolder>(Post.diffCallback) {

    class UserPostItemViewHolder(
        private val binding        : VhProfilePostItemBinding,
        private val onClickPost    : (Post) -> Unit,
        private val onClickBookmark: (Post) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.postItem = item

            itemView.onThrottleClick {
                onClickPost(item)
            }

            binding.ivBookmark.onThrottleClick {
                onClickBookmark(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
       UserPostItemViewHolder(
            VhProfilePostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPost, onClickBookmark
       )

    override fun onBindViewHolder(holder: UserPostItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


