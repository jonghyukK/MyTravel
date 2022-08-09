package org.kjh.mytravel.ui.features.daylog

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.ui.features.daylog.around.AroundPlaceItemDecoration
import org.kjh.mytravel.ui.features.daylog.around.TYPE_AROUND_PLACE_ITEM

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
fun bindAdapterWithGridSpanSizeLayoutManager(rv: RecyclerView, concatAdapter: ConcatAdapter) {
    val gridLayoutManager = GridLayoutManager(rv.context, SPAN_COUNT_2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =
                concatAdapter.getWrappedAdapterAndPosition(position).run {
                    val showGrid =
                        this.first.getItemViewType(this.second) == TYPE_AROUND_PLACE_ITEM
                    if (showGrid)
                        SPAN_COUNT_1
                    else
                        SPAN_COUNT_2
                }
        }
    }

    rv.apply {
        layoutManager = gridLayoutManager
        adapter       = concatAdapter
        addItemDecoration(AroundPlaceItemDecoration())
    }
}

@BindingAdapter("onScrollListenerWithToolbar", "callback")
fun bindOnScrollListenerForToolbarCollapsed(
    rv     : RecyclerView,
    toolbar: Toolbar,
    callback: (Boolean) -> Unit
) {
    rv.addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val toolbarHeight = toolbar.height
            val scrollRange = rv.computeVerticalScrollOffset()
            val compareHeight = rv.getChildAt(0).height

            callback(scrollRange > compareHeight - toolbarHeight)
        }
    })
}