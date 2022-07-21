package org.kjh.mytravel.ui.features.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhSquareImageLargeBinding
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: PostsLinearImageAdapter
 * Created by jonghyukkang on 2022/05/23.
 *
 * Description:
 */

class PostsLinearImageAdapter(
    private val onClickPostImg: () -> Unit
) : RecyclerView.Adapter<PostsLinearImageAdapter.PostLinearInnerViewHolder>() {

    private val postImages = mutableListOf<String>()

    fun setItems(items: List<String>) {
        postImages.clear()
        postImages.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostLinearInnerViewHolder(
            VhSquareImageLargeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPostImg
        )

    override fun onBindViewHolder(holder: PostLinearInnerViewHolder, position: Int) {
        holder.bind(postImages[position])
    }

    override fun getItemCount() = postImages.size

    class PostLinearInnerViewHolder(
        private val binding: VhSquareImageLargeBinding,
        private val onClickPostImg: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.onThrottleClick { onClickPostImg() }
        }

        fun bind(url: String) {
            binding.imageUrl = url
        }
    }
}
