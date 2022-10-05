package org.kjh.mytravel.ui.features.upload.select

import android.net.Uri
import android.view.MotionEvent
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.RecyclerView

/**
 * MyTravel
 * Class: MediaStoreSelectionTracker
 * Created by mac on 2022/01/20.
 *
 * Description:
 */
class MediaStoreSelectionTracker(
    val recyclerView: RecyclerView,
    val onChangedSelection: () -> Unit,
) {
    private val builder: SelectionTracker.Builder<Uri> =
        SelectionTracker.Builder(
            "select_photo",
            recyclerView,
            SelectPhotoIdKeyProvider(recyclerView.adapter as MediaStoreImageListAdapter),
            SelectPhotoDetailsLookup(recyclerView),
            StorageStrategy.createParcelableStorage(Uri::class.java)
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything())

    fun getTracker(): SelectionTracker<Uri> =
        builder.build().apply {
            addObserver(object : SelectionTracker.SelectionObserver<Uri>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    onChangedSelection()
                }
            })
        }

    class SelectPhotoIdKeyProvider(
        private val adapter: MediaStoreImageListAdapter
    ) : ItemKeyProvider<Uri>(SCOPE_CACHED) {

        override fun getKey(position: Int): Uri =
            adapter.currentList[position].contentUri

        override fun getPosition(key: Uri) =
            adapter.currentList.indexOfFirst { it.contentUri == key }
    }

    class SelectPhotoDetailsLookup(
        private val recyclerView: RecyclerView
    ) : ItemDetailsLookup<Uri>() {

        override fun getItemDetails(e: MotionEvent): ItemDetails<Uri>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)

            if (view != null) {
                val holder = recyclerView.getChildViewHolder(view) as
                        MediaStoreImageListAdapter.SelectPhotoViewHolder
                return holder.getItemDetails()
            }

            return null
        }
    }
}