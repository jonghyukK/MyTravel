package org.kjh.mytravel.ui.features.daylog.images

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhDayLogImagesRowBinding
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener
import org.kjh.mytravel.ui.features.daylog.FullLineIndicatorDecoration

/**
 * MyTravel
 * Class: DayLogDetailImagesOuterAdapter
 * Created by jonghyukkang on 2022/08/06.
 *
 * Description:
 */

class DayLogDetailImagesOuterAdapter(
    private val innerAdapter: DayLogDetailImagesInnerAdapter
): RecyclerView.Adapter<DayLogDetailImagesOuterAdapter.DayLogImagesOuterViewHolder>() {

    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayLogImagesOuterViewHolder(
            VhDayLogImagesRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: DayLogImagesOuterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    inner class DayLogImagesOuterViewHolder(
        val binding: VhDayLogImagesRowBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper = PagerSnapHelper()

        init {
            binding.imageRecyclerView.apply {
                setHasFixedSize(true)
                adapter = innerAdapter
                addItemDecoration(FullLineIndicatorDecoration())
                snapHelper.attachToRecyclerView(this)
                addOnScrollListener(
                    OnSnapPagerScrollListener(
                        snapHelper = snapHelper,
                        listener = object: OnSnapPagerScrollListener.OnChangeListener {
                            override fun onSnapped(position: Int) {
                                childViewState[layoutPosition] =
                                    binding.imageRecyclerView.layoutManager?.onSaveInstanceState()
                            }
                        }
                    )
                )
            }
        }

        fun bind() {
            restorePosition()
        }

        private fun restorePosition() {
            val key = layoutPosition
            val state = childViewState[key]

            if (state != null) {
                binding.imageRecyclerView.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.imageRecyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }
}