package org.kjh.mytravel.ui.features.daylog

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.R
import org.kjh.mytravel.ui.features.daylog.around.AroundPlaceItemDecoration
import org.kjh.mytravel.ui.features.daylog.around.AroundPlaceListAdapter.Companion.VIEW_TYPE_AROUND_PLACE_ITEM

/**
 * MyTravel
 * Class: DayLogBindingAdapters
 * Created by jonghyukkang on 2022/08/09.
 *
 * Description:
 */

private const val SPAN_COUNT_2 = 2
private const val SPAN_COUNT_1 = 1

@BindingAdapter("adapter")
fun RecyclerView.bindAdapterWithGridSpanSize(concatAdapter: ConcatAdapter) {
    val gridLayoutManager = GridLayoutManager(context, SPAN_COUNT_2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =
                concatAdapter.getWrappedAdapterAndPosition(position).run {
                    val showGrid = first.getItemViewType(second) == VIEW_TYPE_AROUND_PLACE_ITEM
                    if (showGrid)
                        SPAN_COUNT_1
                    else
                        SPAN_COUNT_2
                }
        }
    }

    itemAnimator  = null
    layoutManager = gridLayoutManager
    adapter       = concatAdapter
    addItemDecoration(AroundPlaceItemDecoration())
}

@BindingAdapter("onScrollActionPlaceDetail")
fun RecyclerView.bindOnScrollListener(scrollAction: (Boolean) -> Unit) {
    clearOnScrollListeners()
    addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val toolbarHeight = rootView.findViewById<Toolbar>(R.id.tb_placeDetailToolbar)?.height ?: 0
            val scrollRange = computeVerticalScrollOffset()
            val compareHeight = findViewById<RecyclerView>(R.id.imageRecyclerView)?.height ?: 0

            scrollAction(scrollRange > compareHeight - toolbarHeight)
        }
    })
}
