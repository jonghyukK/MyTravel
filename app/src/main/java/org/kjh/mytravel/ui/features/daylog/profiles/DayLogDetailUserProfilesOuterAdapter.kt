package org.kjh.mytravel.ui.features.daylog.profiles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhDayLogUserProfileRowBinding

/**
 * MyTravel
 * Class: DayLogDetailUserProfilesOuterAdapter
 * Created by jonghyukkang on 2022/08/06.
 *
 * Description:
 */


class DayLogDetailUserProfilesOuterAdapter(
    private val innerAdapter: DayLogDetailUserProfilesInnerAdapter
): RecyclerView.Adapter<DayLogDetailUserProfilesOuterAdapter.DayLogDetailUserProfilesOuterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayLogDetailUserProfilesOuterViewHolder(
            VhDayLogUserProfileRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), innerAdapter
        )

    override fun onBindViewHolder(holder: DayLogDetailUserProfilesOuterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    class DayLogDetailUserProfilesOuterViewHolder(
        private val binding     : VhDayLogUserProfileRowBinding,
        private val innerAdapter: DayLogDetailUserProfilesInnerAdapter
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.userProfileRecyclerView.apply {
                setHasFixedSize(true)
                adapter = innerAdapter
                addItemDecoration(DayLogDetailUserProfileItemDecoration())
            }
        }

        fun bind() {}
    }
}
