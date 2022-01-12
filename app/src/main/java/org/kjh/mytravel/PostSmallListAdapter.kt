package org.kjh.mytravel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPostSmallItemBinding

/**
 * MyTravel
 * Class: PostSmallListAdapter
 * Created by mac on 2022/01/11.
 *
 * Description:
 */
class PostSmallListAdapter(
    private val items: List<PostItem>,
    private val onClickItem: (PostItem) -> Unit
): RecyclerView.Adapter<PostItemSmallViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostItemSmallViewHolder(
            VhPostSmallItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickItem
        )

    override fun onBindViewHolder(holder: PostItemSmallViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}

class PostItemSmallViewHolder(
    val binding: VhPostSmallItemBinding,
    private val onClickItem: (PostItem) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PostItem) {
        binding.postItem = item
        binding.cvPostItemSmallContainer.setOnClickListener {
            onClickItem(item)
        }
    }
}