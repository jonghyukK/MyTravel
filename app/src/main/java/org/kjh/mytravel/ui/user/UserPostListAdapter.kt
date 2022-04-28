package org.kjh.mytravel.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.kjh.mytravel.databinding.VhProfilePostItemBinding
import org.kjh.mytravel.model.Post

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
): ListAdapter<Post, UserPostItemViewHolder>(Post.DiffCallback) {

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


