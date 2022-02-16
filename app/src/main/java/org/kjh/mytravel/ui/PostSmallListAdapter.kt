package org.kjh.mytravel.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Post
import com.orhanobut.logger.Logger
import org.kjh.mytravel.databinding.VhBookmarkPostItemBinding
import org.kjh.mytravel.databinding.VhProfilePostItemBinding

/**
 * MyTravel
 * Class: PostSmallListAdapter
 * Created by mac on 2022/01/11.
 *
 * Description:
 */
class PostSmallListAdapter(
    private val holderType: Int = 0,
    private val onClickItem: (Post) -> Unit,
    private val onClickBookmark: (Post) -> Unit
): ListAdapter<Post, RecyclerView.ViewHolder>(Post.DiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return holderType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> PostItemSmallViewHolder(
            VhProfilePostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickItem, onClickBookmark
        )
        else -> BookmarkItemSmallViewHolder(
            VhBookmarkPostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickItem, onClickBookmark
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            0 -> (holder as PostItemSmallViewHolder).bind(getItem(position))
            1 -> (holder as BookmarkItemSmallViewHolder).bind(getItem(position))
        }
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

class BookmarkItemSmallViewHolder(
    val binding: VhBookmarkPostItemBinding,
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


