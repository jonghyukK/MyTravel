package org.kjh.mytravel.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentWritePostBinding
import org.kjh.mytravel.databinding.VhRectImageBigBinding
import androidx.recyclerview.widget.SnapHelper
import org.kjh.mytravel.dpToPx


class TempImageListAdapter
    : ListAdapter<MediaStoreImage, TempImagesViewHolder>(MediaStoreImage.DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TempImagesViewHolder(
            VhRectImageBigBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TempImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TempImagesViewHolder(
    val binding: VhRectImageBigBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaStoreImage) {
        binding.item = item

        val displayWidth: Int = itemView.resources.displayMetrics.widthPixels
        binding.clRectImgContainer.layoutParams.width =
            displayWidth - 16.dpToPx(itemView.resources.displayMetrics) * 4
    }
}

@AndroidEntryPoint
class WritePostFragment : Fragment() {

    private lateinit var binding: FragmentWritePostBinding
    private val parentViewModel : SelectPhotoViewModel by navGraphViewModels(R.id.nav_nested_upload) { defaultViewModelProviderFactory }

    private val tempImageListAdapter by lazy {
        TempImageListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritePostBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = parentViewModel

        initToolbarWithNavigation()
        initImageRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.uiState.collect {
                    tempImageListAdapter.submitList(it.selectedItems)
                }
            }
        }
    }

    private fun initImageRecyclerView() {
        binding.rvSelectedImages.apply {
            adapter = tempImageListAdapter

            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbWritePostToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_upload)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.upload -> {
                        parentViewModel.upload()
                        true
                    }
                    else -> false
                }
            }
        }
    }
}