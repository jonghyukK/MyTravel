package org.kjh.mytravel.ui.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceByCityNameItemBinding
import org.kjh.mytravel.model.Post

/**
 * MyTravel
 * Class: PlaceListByCityNamePostListAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlaceListByCityNamePostListAdapter(
    private val onClickPostItem: (Post) -> Unit
) : ListAdapter<Post, PlaceListByCityNameItemViewHolder>(Post.DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PlaceListByCityNameItemViewHolder(
        VhPlaceByCityNameItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), onClickPostItem
    )

    override fun onBindViewHolder(holder: PlaceListByCityNameItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PlaceListByCityNameItemViewHolder(
    private val binding: VhPlaceByCityNameItemBinding,
    private val onClickPostItem: (Post) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) {
        binding.postItem = item
        itemView.setOnClickListener {
            onClickPostItem(item)
        }
    }
}