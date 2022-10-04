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
class BannerHorizontalWrapAdapter(
    private val bannerListAdapter: BannerListAdapter
) : RecyclerView.Adapter<BannerHorizontalWrapAdapter.HomeBannersOuterViewHolder>() {
    private val childViewState = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeBannersOuterViewHolder(
            VhHomeBannerRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), bannerListAdapter, childViewState
        )

    override fun onBindViewHolder(holder: HomeBannersOuterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    class HomeBannersOuterViewHolder(
        private val binding: VhHomeBannerRowBinding,
        private val bannerListAdapter: BannerListAdapter,
        private val childViewState: MutableMap<Int, Parcelable?>
    ): RecyclerView.ViewHolder(binding.root) {
        private val snapHelper = PagerSnapHelper()

        init {
            binding.bannerRecyclerView.apply {
                setHasFixedSize(true)
                adapter = bannerListAdapter
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


