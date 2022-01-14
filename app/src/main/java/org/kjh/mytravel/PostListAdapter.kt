package org.kjh.mytravel

import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPostItemBinding
import org.kjh.mytravel.uistate.PostItemUiState

/**
 * MyTravel
 * Class: PostItemViewHolder
 * Created by mac on 2022/01/10.
 *
 * Description:
 */

class PostListAdapter(
    private val onClick: (PostItemUiState) -> Unit,
): ListAdapter<PostItemUiState, PostListAdapter.PostItemViewHolder>(PostItemUiState.DiffCallback) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val snapState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
     return PostItemViewHolder(
            VhPostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: PostItemViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        snapState[key] = holder.binding.vpPostItemPager.layoutManager?.onSaveInstanceState()
    }

    inner class PostItemViewHolder(
        val binding: VhPostItemBinding,
        val onClick: (PostItemUiState) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PostItemUiState) {
            binding.postItemUiState = item

            binding.group.referencedIds.forEach { id ->
                binding.root.findViewById<View>(id).setOnClickListener {
                    onClick(item)
                }
            }

            binding.vpPostItemPager.apply {
                setRecycledViewPool(viewPool)
                adapter = RectImageListAdapter(item.postImages) {}
                setHasFixedSize(true)

                val pagerSnapHelper = PagerSnapHelper()
                if (this.onFlingListener == null) {
                    pagerSnapHelper.attachToRecyclerView(this)
                }
            }

            restoreViewState()
        }

        private fun restoreViewState() {
            val key = layoutPosition
            val state = snapState[key]

            if (state != null) {
                binding.vpPostItemPager.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.vpPostItemPager.layoutManager?.scrollToPosition(0)
            }
        }
    }
}