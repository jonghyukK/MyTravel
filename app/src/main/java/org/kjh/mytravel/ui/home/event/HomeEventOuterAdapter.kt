package org.kjh.mytravel.ui.home.event

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.LinearLayoutItemDecoration
import org.kjh.mytravel.databinding.ItemEventListBinding
import org.kjh.mytravel.ui.uistate.EventItemUiState
import org.kjh.mytravel.ui.uistate.PlaceItemUiState

/**
 * MyTravel
 * Class: HomeEventListOuterAdapter
 * Created by mac on 2022/01/07.
 *
 * Description:
 */
class HomeEventOuterAdapter(
    private val onClickEventItem: (PlaceItemUiState) -> Unit
): ListAdapter<EventItemUiState, HomeEventOuterAdapter.HomeEventListOuterViewHolder>(EventItemUiState.DiffCallback) {
    private val scrollState = mutableMapOf<Int, Parcelable?>()
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = HomeEventListOuterViewHolder(
        ItemEventListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), viewPool
    )

    override fun onBindViewHolder(holder: HomeEventListOuterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: HomeEventListOuterViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        scrollState[key] = holder.binding.rvEventInnerList.layoutManager?.onSaveInstanceState()
    }

    inner class HomeEventListOuterViewHolder(
        val binding: ItemEventListBinding,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EventItemUiState) {
            binding.eventItemUiState = item

            binding.rvEventInnerList.apply {
                adapter = HomeEventInnerAdapter(item.placeItems) { item ->
                    onClickEventItem(item)
                }
                setHasFixedSize(true)
                setRecycledViewPool(viewPool)
                addItemDecoration(LinearLayoutItemDecoration(binding.root.context, 20, 20, 0, 15))
            }

            restoreViewState()
        }

        private fun restoreViewState() {
            val key = layoutPosition
            val state = scrollState[key]

            if (state != null) {
                binding.rvEventInnerList.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvEventInnerList.layoutManager?.scrollToPosition(0)
            }
        }
    }
}