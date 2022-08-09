package org.kjh.mytravel.ui.features.daylog.around


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.databinding.VhAroundPlaceHeaderItemBinding
import org.kjh.mytravel.databinding.VhAroundPlaceItemBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.ui.features.daylog.AroundPlaceUiModel
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: RelatedPlacesListAdapter
 * Created by jonghyukkang on 2022/08/03.
 *
 * Description:
 */

class AroundPlaceListAdapter
    : PagingDataAdapter<AroundPlaceUiModel, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {

    override fun getItemViewType(position: Int) =
        when (getItem(position)) {
            is AroundPlaceUiModel.PlaceItem -> TYPE_AROUND_PLACE_ITEM
            is AroundPlaceUiModel.HeaderItem -> TYPE_AROUND_PLACE_HEADER_ITEM
            null -> throw IllegalArgumentException("Not Found ViewHolder Type")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            TYPE_AROUND_PLACE_ITEM -> AroundPlaceItemViewHolder.create(parent)
            TYPE_AROUND_PLACE_HEADER_ITEM -> AroundPlaceHeaderItemViewHolder.create(parent)
            else -> throw IllegalArgumentException("Not Found ViewHolder Type $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { uiModel ->
            when (uiModel) {
                is AroundPlaceUiModel.PlaceItem -> (holder as AroundPlaceItemViewHolder).bind(uiModel.place)
                is AroundPlaceUiModel.HeaderItem -> (holder as AroundPlaceHeaderItemViewHolder).bind(uiModel.headerTitle)
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
            itemView.onThrottleClick { view ->
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

    companion object {
        const val TYPE_AROUND_PLACE_ITEM = 1
        const val TYPE_AROUND_PLACE_HEADER_ITEM = 2

        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<AroundPlaceUiModel>() {
            override fun areItemsTheSame(oldItem: AroundPlaceUiModel, newItem: AroundPlaceUiModel) =
                (oldItem is AroundPlaceUiModel.PlaceItem && newItem is AroundPlaceUiModel.PlaceItem &&
                        oldItem.place.placeName == newItem.place.placeName) ||
                        (oldItem is AroundPlaceUiModel.HeaderItem && newItem is AroundPlaceUiModel.HeaderItem &&
                                oldItem.headerTitle == newItem.headerTitle)

            override fun areContentsTheSame(oldItem: AroundPlaceUiModel, newItem: AroundPlaceUiModel) =
                oldItem == newItem
        }
    }
}