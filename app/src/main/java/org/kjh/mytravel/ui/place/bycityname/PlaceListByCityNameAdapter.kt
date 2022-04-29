package org.kjh.mytravel.ui.place.bycityname

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhPlaceByCityNameRowBinding
import org.kjh.mytravel.model.Place

/**
 * MyTravel
 * Class: PlaceListByCityNameAdapter
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlaceListByCityNameAdapter(
    private val onClickPlaceItem: (String) -> Unit
) : ListAdapter<Place, PlaceListByCityNameViewHolder>(Place.DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PlaceListByCityNameViewHolder(
        VhPlaceByCityNameRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), onClickPlaceItem
    )

    override fun onBindViewHolder(holder: PlaceListByCityNameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PlaceListByCityNameViewHolder(
    private val binding: VhPlaceByCityNameRowBinding,
    private val onClickPlaceItem: (String) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    private val postListAdapter = PlaceListByCityNamePostListAdapter {
        onClickPlaceItem(it.placeName)
    }

    init {
        binding.rvPostList.apply {
            adapter = postListAdapter
            setHasFixedSize(true)
            addItemDecoration(PostListInPlaceListByCityNameItemDecoration())
        }
    }

    fun bind(item: Place) {
        binding.placeItem = item
        postListAdapter.submitList(item.posts)

        itemView.setOnClickListener {
            onClickPlaceItem(item.placeName)
        }
    }
}
