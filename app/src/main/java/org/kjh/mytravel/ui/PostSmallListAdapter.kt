package org.kjh.mytravel.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Post
import com.orhanobut.logger.Logger
import org.kjh.mytravel.databinding.VhProfilePostItemBinding

/**
 * MyTravel
 * Class: PostSmallListAdapter
 * Created by mac on 2022/01/11.
 *
 * Description:
 */
class PostSmallListAdapter(
    private val onClickItem: (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): ListAdapter<Post, PostItemSmallViewHolder>(Post.DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostItemSmallViewHolder(
            VhProfilePostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickItem, onClickBookmark
        )

    override fun onBindViewHolder(holder: PostItemSmallViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostItemSmallViewHolder(
    val binding: VhProfilePostItemBinding,
    private val onClickItem: (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) {
        binding.postItem = item

        itemView.setOnClickListener {
            onClickItem(item)
        }

        binding.ivBookmark.setOnClickListener {
            onClickBookmark(item)
        }
    }
}

