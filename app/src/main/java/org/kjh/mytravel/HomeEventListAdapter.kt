package org.kjh.mytravel

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import org.kjh.mytravel.databinding.ItemEventInnerListBinding
import org.kjh.mytravel.databinding.ItemEventListBinding

/**
 * MyTravel
 * Class: HomeEventListAdapter
 * Created by mac on 2022/01/03.
 *
 * Description:
 */
class HomeEventListOuterAdapter(
    private val eventList: List<EventItem>,
    private val onClickEventItem: (CityItem) -> Unit
): RecyclerView.Adapter<HomeEventListOuterAdapter.HomeEventListOuterViewHolder>() {

    private val scrollState = mutableMapOf<Int, Parcelable?>()
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = HomeEventListOuterViewHolder(
        ItemEventListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), onClickEventItem, viewPool
    )

    override fun onBindViewHolder(holder: HomeEventListOuterViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount() = eventList.size

    override fun onViewRecycled(holder: HomeEventListOuterViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        scrollState[key] = holder.binding.rvEventInnerList.layoutManager?.onSaveInstanceState()
    }

    inner class HomeEventListOuterViewHolder(
        val binding: ItemEventListBinding,
        val onClickEventItem: (CityItem) -> Unit,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EventItem) {
            binding.eventItem = item

            binding.rvEventInnerList.apply {
                layoutManager = LinearLayoutManager(binding.root.context).apply {
                    orientation = HORIZONTAL
                }
                adapter = HomeEventInnerAdapter(item.itemList) { item ->
                    onClickEventItem(item)
                }
                setHasFixedSize(true)
                setRecycledViewPool(viewPool)
                addItemDecoration(LinearLayoutItemDecoration(binding.root.context, 20, 20, 15, 15))
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

class HomeEventInnerAdapter(
    private val eventInnerList: List<CityItem>,
    private val onClickItem: (CityItem) -> Unit
) : RecyclerView.Adapter<HomeEventInnerAdapter.CityListHorizontalViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityListHorizontalViewHolder {
        return CityListHorizontalViewHolder(
            ItemEventInnerListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickItem
        )
    }

    override fun onBindViewHolder(holder: CityListHorizontalViewHolder, position: Int) {
        holder.bind(eventInnerList[position])
    }

    override fun getItemCount() = eventInnerList.size

    class CityListHorizontalViewHolder(
        private val binding: ItemEventInnerListBinding,
        private val onClickItem: (CityItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cityItem: CityItem) {
            binding.cityItem = cityItem
            binding.ivEventImg.setOnClickListener {
                onClickItem(cityItem)
            }
        }
    }
}
