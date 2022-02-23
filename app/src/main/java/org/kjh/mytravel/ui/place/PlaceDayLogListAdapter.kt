package org.kjh.mytravel.ui.place

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Post
import org.kjh.mytravel.databinding.VhPlaceDayLogItemBinding
import org.kjh.mytravel.ui.RectImageListAdapter

/**
 * MyTravel
 * Class: PostItemViewHolder
 * Created by mac on 2022/01/10.
 *
 * Description:
 */

class PlaceDayLogListAdapter(
    private val onClickDayLog: (Post) -> Unit,
): ListAdapter<Post, PlaceDayLogListAdapter.PlaceDayLogViewHolder>(Post.DiffCallback) {
    private val lmState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceDayLogViewHolder(
            VhPlaceDayLogItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceDayLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: PlaceDayLogViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        lmState[key] = holder.binding.rvDayLogImgList.layoutManager?.onSaveInstanceState()
    }

    inner class PlaceDayLogViewHolder(
        val binding: VhPlaceDayLogItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) {
            binding.post = item

            binding.group.referencedIds.forEach { id ->
                binding.root.findViewById<View>(id).setOnClickListener {
                    onClickDayLog(item)
                }
            }

            binding.rvDayLogImgList.apply {
                adapter = RectImageListAdapter(item.imageUrl) {}
                setHasFixedSize(true)

                if (onFlingListener == null) {
                    val pagerSnapHelper = PagerSnapHelper()
                    pagerSnapHelper.attachToRecyclerView(this)
                }
            }

            restoreViewState()
        }

        private fun restoreViewState() {
            val key = layoutPosition
            val state = lmState[key]

            if (state != null) {
                binding.rvDayLogImgList.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvDayLogImgList.layoutManager?.scrollToPosition(0)
            }
        }
    }
}