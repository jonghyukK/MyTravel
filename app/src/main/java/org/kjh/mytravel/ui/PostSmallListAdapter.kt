package org.kjh.mytravel.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhBookmarkPostItemBinding
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post

/**
 * MyTravel
 * Class: PostSmallListAdapter
 * Created by mac on 2022/01/11.
 *
 * Description:
 */
class PostSmallListAdapter(
    private val onClickPost    : (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): ListAdapter<Post, PostItemSmallViewHolder>(Post.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
       PostItemSmallViewHolder(
            VhProfilePostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPost, onClickBookmark
       )

    override fun onBindViewHolder(holder: PostItemSmallViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostItemSmallViewHolder(
    val binding: VhProfilePostItemBinding,
    private val onClickPost    : (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) {
        binding.postItem = item

        itemView.setOnClickListener {
            onClickPost(item)
        }

        binding.ivBookmark.setOnClickListener {
            onClickBookmark(item)
        }
    }
}



