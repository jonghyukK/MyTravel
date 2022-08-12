package org.kjh.mytravel.ui.features.home.banner

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhHomeBannerRowBinding
import org.kjh.mytravel.ui.common.OnNestedHorizontalTouchListener
import org.kjh.mytravel.ui.common.OnSnapPagerScrollListener

/**
 * MyTravel
 * Class: BannerOuterAdapter
 * Created by jonghyukkang on 2022/08/11.
 *
 * Description:
 */
class BannerOuterAdapter(
    private val bannerInnerAdapter : BannerListAdapter
): RecyclerView.Adapter<BannerOuterAdapter.HomeBannersOuterViewHolder>() {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeBannersOuterViewHolder(
            VhHomeBannerRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: HomeBannersOuterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    inner class HomeBannersOuterViewHolder(
        val binding: VhHomeBannerRowBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper = PagerSnapHelper()

        init {
            binding.bannerRecyclerView.apply {
                setHasFixedSize(true)
                adapter = bannerInnerAdapter
                addItemDecoration(BannerItemDecoration())
                snapHelper.attachToRecyclerView(this)
                addOnItemTouchListener(OnNestedHorizontalTouchListener())
                addOnScrollListener(
                    OnSnapPagerScrollListener(
                        snapHelper = snapHelper,
                        listener = object: OnSnapPagerScrollListener.OnChangeListener {
                            override fun onSnapped(position: Int) {
                                childViewState[layoutPosition] =
                                    binding.bannerRecyclerView.layoutManager?.onSaveInstanceState()
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
                binding.bannerRecyclerView.layoutManager?.onRestoreInstanceState(state)
            } else {
                binding.bannerRecyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }
}


