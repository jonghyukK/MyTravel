package org.kjh.mytravel

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import org.kjh.mytravel.databinding.VhPlaceItemBinding

/**
 * MyTravel
 * Class: PlaceListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */
class PlaceListAdapter(
    private val items: List<PlaceItem>,
    private val onClickItem: (PlaceItem) -> Unit
): RecyclerView.Adapter<PlaceListAdapter.PlaceItemViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val state = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceItemViewHolder(
            VhPlaceItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun onViewRecycled(holder: PlaceItemViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        state[key] = holder.binding.rvRecentPlaceList.layoutManager?.onSaveInstanceState()
    }

    inner class PlaceItemViewHolder(
        val binding: VhPlaceItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceItem) {
            binding.placeItem = item

            binding.clPlaceItemContainer.setOnClickListener {
                onClickItem(item)
            }

            binding.rvRecentPlaceList.apply {
                setRecycledViewPool(viewPool)
                adapter = RectImageListAdapter(item.prevImgList) {
                    onClickItem(item)
                }
                setHasFixedSize(true)

                val pagerSnapHelper = PagerSnapHelper()
                if (this.onFlingListener == null) {
                    pagerSnapHelper.attachToRecyclerView(this)
                }
            }

            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = state[key]

            if (state != null) {
                binding.rvRecentPlaceList.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvRecentPlaceList.layoutManager?.scrollToPosition(0)
            }
        }
    }
}