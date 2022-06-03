package org.kjh.mytravel.ui.features.home.latest

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import org.kjh.mytravel.databinding.VhLatestPostsPagingLoadStateBinding

/**
 * MyTravel
 * Class: LatestPostPagingLoadStateAdapter
 * Created by jonghyukkang on 2022/05/13.
 *
 * Description:
 */
class LatestPostPagingLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<LatestPostPagingLoadStateAdapter.LatestPostPagingLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LatestPostPagingLoadStateViewHolder(
            VhLatestPostsPagingLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), retry
        )

    override fun onBindViewHolder(
        holder: LatestPostPagingLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    class LatestPostPagingLoadStateViewHolder(
        private val binding: VhLatestPostsPagingLoadStateBinding,
        private val retry  : () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retry()
            }
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