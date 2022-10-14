package org.kjh.mytravel.ui.features.daylog.around


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.VhAroundPlaceHeaderItemBinding
import org.kjh.mytravel.databinding.VhAroundPlaceItemBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.common.PagingWithHeaderUiModel
import org.kjh.mytravel.ui.common.setOnThrottleClickListener
import org.kjh.mytravel.utils.navigateTo


/**
 * MyTravel
 * Class: RelatedPlacesListAdapter
 * Created by jonghyukkang on 2022/08/03.
 *
 * Description:
 */

class AroundPlaceListAdapter
    : PagingDataAdapter<PagingWithHeaderUiModel<Place>, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun getItemViewType(position: Int) = when (peek(position)) {
            is PagingWithHeaderUiModel.HeaderItem -> VIEW_TYPE_AROUND_HEADER_ITEM
            is PagingWithHeaderUiModel.PagingItem -> VIEW_TYPE_AROUND_PLACE_ITEM
            null -> throw IllegalArgumentException("Not Found ViewHolder Type")
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        VIEW_TYPE_AROUND_HEADER_ITEM -> AroundPlaceHeaderItemViewHolder.create(parent)
        VIEW_TYPE_AROUND_PLACE_ITEM -> AroundPlaceItemViewHolder.create(parent)
        else -> throw Exception("Unknown View Type")
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (item is PagingWithHeaderUiModel.HeaderItem) {
            (holder as AroundPlaceHeaderItemViewHolder).bind("주변 가볼만한 장소")
        } else if (item is PagingWithHeaderUiModel.PagingItem) {
            (holder as AroundPlaceItemViewHolder).bind(item.item)
        }
    }

    companion object {
        const val VIEW_TYPE_AROUND_PLACE_ITEM = R.layout.vh_around_place_item
        const val VIEW_TYPE_AROUND_HEADER_ITEM = R.layout.vh_around_place_header_item

        private val UIMODEL_COMPARATOR =
            object : DiffUtil.ItemCallback<PagingWithHeaderUiModel<Place>>() {
                override fun areItemsTheSame(
                    oldItem: PagingWithHeaderUiModel<Place>,
                    newItem: PagingWithHeaderUiModel<Place>
                ): Boolean {
                    val isSameHeaderItem = oldItem is PagingWithHeaderUiModel.HeaderItem
                            && newItem is PagingWithHeaderUiModel.HeaderItem

                    val isSamePagingItem = oldItem is PagingWithHeaderUiModel.PagingItem
                            && newItem is PagingWithHeaderUiModel.PagingItem
                            && oldItem.item.placeName == newItem.item.placeName

                    return isSameHeaderItem || isSamePagingItem
                }

                override fun areContentsTheSame(
                    oldItem: PagingWithHeaderUiModel<Place>,
                    newItem: PagingWithHeaderUiModel<Place>
                ) = oldItem == newItem
            }
    }
}

// Around Place Header Item VH.
class AroundPlaceHeaderItemViewHolder(
    private val binding: VhAroundPlaceHeaderItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(headerText: String) {
        binding.headerTitle = headerText
    }

    companion object {
        fun create(parent: ViewGroup) =
            AroundPlaceHeaderItemViewHolder(
                VhAroundPlaceHeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}

// Around Place Item VH.
class AroundPlaceItemViewHolder(
    private val binding: VhAroundPlaceItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnThrottleClickListener { view ->
            binding.placeItem?.let { place ->
                view.navigateTo(
                    NavGraphDirections.actionGlobalDayLogDetailFragment(place.placeName))
            }
        }
    }

    fun bind(item: Place) {
        binding.placeItem = item
    }

    companion object {
        fun create(parent: ViewGroup) =
            AroundPlaceItemViewHolder(
                VhAroundPlaceItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }
}