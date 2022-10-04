package org.kjh.mytravel.ui.features.home.latest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhLatestDayLogsPagingLoadStateBinding

/**
 * MyTravel
 * Class: LatestDayLogPagingLoadStateAdapter
 * Created by jonghyukkang on 2022/05/13.
 *
 * Description:
 */
class LatestDayLogPagingLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<LatestDayLogPagingLoadStateAdapter.LatestDayLogPagingLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LatestDayLogPagingLoadStateViewHolder(
            VhLatestDayLogsPagingLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), retry
        )

    override fun onBindViewHolder(
        holder: LatestDayLogPagingLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    class LatestDayLogPagingLoadStateViewHolder(
        private val binding: VhLatestDayLogsPagingLoadStateBinding,
        private val retry  : () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(state: LoadState) {
            binding.isLoading = state is LoadState.Loading
            binding.isError   = state is LoadState.Error

            if (state is LoadState.Error) {
                binding.errorMsg = (state as? LoadState.Error)?.error?.message ?: ""
            }
        }
    }
}