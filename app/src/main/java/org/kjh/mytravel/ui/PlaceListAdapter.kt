package org.kjh.mytravel.ui

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceItemBinding
import org.kjh.mytravel.ui.uistate.PlaceItemUiState

/**
 * MyTravel
 * Class: PlaceListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */
class PlaceListAdapter(
    private val onClickItem: (PlaceItemUiState) -> Unit
): ListAdapter<PlaceItemUiState, PlaceListAdapter.PlaceItemViewHolder>(PlaceItemUiState.DiffCallback) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val state = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceItemViewHolder(
            VhPlaceItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: PlaceItemViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        state[key] = holder.binding.rvRecentPlaceList.layoutManager?.onSaveInstanceState()
    }

    inner class PlaceItemViewHolder(
        val binding: VhPlaceItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceItemUiState) {
            binding.placeItemUiState = item

            binding.clPlaceItemContainer.setOnClickListener {
                onClickItem(item)
            }

            binding.rvRecentPlaceList.apply {
                setRecycledViewPool(viewPool)
//                adapter = RectImageListAdapter(item.placeImages) {
//                    onClickItem(item)
//                }
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