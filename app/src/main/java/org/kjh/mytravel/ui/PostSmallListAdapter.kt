package org.kjh.mytravel.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPostSmallItemBinding
import org.kjh.mytravel.ui.uistate.PostItemUiState

/**
 * MyTravel
 * Class: PostSmallListAdapter
 * Created by mac on 2022/01/11.
 *
 * Description:
 */
class PostSmallListAdapter(
    private val onClickItem: (PostItemUiState) -> Unit
): ListAdapter<PostItemUiState, PostItemSmallViewHolder>(PostItemUiState.DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostItemSmallViewHolder(
            VhPostSmallItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickItem
        )

    override fun onBindViewHolder(holder: PostItemSmallViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostItemSmallViewHolder(
    val binding: VhPostSmallItemBinding,
    private val onClickItem: (PostItemUiState) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PostItemUiState) {
        binding.postItemUiState = item
        binding.cvPostItemSmallContainer.setOnClickListener {
            onClickItem(item)
        }
    }
}