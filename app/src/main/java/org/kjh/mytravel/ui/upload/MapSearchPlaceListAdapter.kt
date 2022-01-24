package org.kjh.mytravel.ui.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.data.model.KakaoSearchPlaceModel
import org.kjh.mytravel.databinding.VhBsSearchPlaceBinding

/**
 * MyTravel
 * Class: MapSearchPlaceListAdapter
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */
class MapSearchPlaceListAdapter(
    private val onClickPlace: (KakaoSearchPlaceModel) -> Unit
) : ListAdapter<KakaoSearchPlaceModel, MapSearchPlaceListAdapter.MapSearchPlaceViewHolder>(
    KakaoSearchPlaceModel.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MapSearchPlaceViewHolder(
            VhBsSearchPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickPlace
        )

    override fun onBindViewHolder(holder: MapSearchPlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MapSearchPlaceViewHolder(
        val binding: VhBsSearchPlaceBinding,
        val onClickPlace: (KakaoSearchPlaceModel) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KakaoSearchPlaceModel) {
            binding.kakaoPlaceModel = item

            itemView.setOnClickListener {
                onClickPlace(item)
            }
        }
    }
}