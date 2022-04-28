package org.kjh.mytravel.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post

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
): ListAdapter<Post, MyPostItemViewHolder>(Post.DiffCallback) {

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