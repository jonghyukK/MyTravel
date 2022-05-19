package org.kjh.mytravel.ui.features.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: MyPostListAdapter
 * Created by jonghyukkang on 2022/04/27.
 *
 * Description:
 */
class MyPostListAdapter(
    private val onClickPost    : (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): ListAdapter<Post, MyPostListAdapter.MyPostItemViewHolder>(Post.diffCallback) {

    class MyPostItemViewHolder(
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
        MyPostItemViewHolder(
            VhProfilePostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPost, onClickBookmark
        )

    override fun onBindViewHolder(holder: MyPostItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}