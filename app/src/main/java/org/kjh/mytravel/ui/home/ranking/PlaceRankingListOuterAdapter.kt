package org.kjh.mytravel.ui.home.ranking

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.LinearLayoutItemDecoration
import org.kjh.mytravel.databinding.VhPlaceRankingRowBinding

/**
 * MyTravel
 * Class: HomeAdapter
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
class PlaceRankingListOuterAdapter(
    private val placeRankingAdapter: PlaceRankingListAdapter
): RecyclerView.Adapter<PlaceRankingListOuterAdapter.PlaceRankingRowViewHolder>() {
    private val state = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlaceRankingRowViewHolder(
            VhPlaceRankingRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PlaceRankingRowViewHolder, position: Int) {
        holder.bind(placeRankingAdapter)
    }

    override fun onViewRecycled(holder: PlaceRankingRowViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        state[key] = holder.binding.rvPlaceRankingListConcat.layoutManager?.onSaveInstanceState()
    }

    override fun getItemCount() = 1

    inner class PlaceRankingRowViewHolder(
        val binding: VhPlaceRankingRowBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(placeRankingAdapter: PlaceRankingListAdapter) {
            binding.rvPlaceRankingListConcat.apply {
                adapter = placeRankingAdapter
                setHasFixedSize(true)

                if (itemDecorationCount == 0) {
                    addItemDecoration(
                        LinearLayoutItemDecoration(
                            this.context, 20, 20, 0, 0
                        )
                    )
                }

                if (onFlingListener == null) {
                    val pagerSnapHelper = PagerSnapHelper()
                    pagerSnapHelper.attachToRecyclerView(this)
                }
            }

            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = state[key]

            if (state != null) {
                binding.rvPlaceRankingListConcat.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.rvPlaceRankingListConcat.layoutManager?.scrollToPosition(0)
            }
        }
    }
}
