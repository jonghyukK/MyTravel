package org.kjh.mytravel.ui.features.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mytravel.databinding.VhRectImageFullSquareBinding
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

    class PostLinearInnerViewHolder(
        private val binding: VhRectImageFullSquareBinding,
        private val onClickPostImg: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.imageUrl = url

            itemView.onThrottleClick {
                onClickPostImg()
            }
        }
    }

    private val postImages = mutableListOf<String>()

    fun setItems(items: List<String>) {
        postImages.clear()
        postImages.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostLinearInnerViewHolder(
            VhRectImageFullSquareBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPostImg
        )

    override fun onBindViewHolder(holder: PostLinearInnerViewHolder, position: Int) {
        holder.bind(postImages[position])
    }

    override fun getItemCount() = postImages.size
}
