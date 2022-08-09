package org.kjh.mytravel.ui.features.daylog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentDaylogDetailBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.daylog.around.AroundPlaceItemDecoration
import org.kjh.mytravel.ui.features.daylog.around.AroundPlaceListAdapter
import org.kjh.mytravel.ui.features.daylog.around.TYPE_AROUND_PLACE_ITEM
import org.kjh.mytravel.ui.features.daylog.contents.DayLogDetailItemAdapter
import org.kjh.mytravel.ui.features.daylog.images.DayLogDetailImagesInnerAdapter
import org.kjh.mytravel.ui.features.daylog.images.DayLogDetailImagesOuterAdapter
import org.kjh.mytravel.ui.features.daylog.profiles.DayLogDetailUserProfilesInnerAdapter
import org.kjh.mytravel.ui.features.daylog.profiles.DayLogDetailUserProfilesOuterAdapter
import javax.inject.Inject

@AndroidEntryPoint
class DayLogDetailFragment
    : BaseFragment<FragmentDaylogDetailBinding>(R.layout.fragment_daylog_detail) {

    @Inject
    lateinit var dayLogDetailViewModelFactory: DayLogDetailViewModel.DayLogDetailAssistedFactory

    private val args: DayLogDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<DayLogDetailViewModel> {
        DayLogDetailViewModel.provideFactory(dayLogDetailViewModelFactory, args.placeName, args.postId)
    }

    private val imagesInnerAdapter by lazy {
        DayLogDetailImagesInnerAdapter()
    }

    private val profilesInnerAdapter by lazy {
        DayLogDetailUserProfilesInnerAdapter {
            viewModel.changeCurrentPostItem(it)
        }
    }

    private val contentAdapter by lazy {
        DayLogDetailItemAdapter()
    }

    private val aroundPlaceListAdapter by lazy {
        AroundPlaceListAdapter()
    }

    private val concatAdapter by lazy {
        ConcatAdapter(
            DayLogDetailImagesOuterAdapter(imagesInnerAdapter),
            DayLogDetailUserProfilesOuterAdapter(profilesInnerAdapter),
            contentAdapter,
            aroundPlaceListAdapter
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.concatAdapter = concatAdapter
        binding.placeName = args.placeName

        binding.tbPlaceDetailToolbar.setupWithNavController(findNavController())
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        uiState.currentPostItem?.let {
                            contentAdapter.setPostItem(it)
                            imagesInnerAdapter.submitList(it.imageUrl)
                            profilesInnerAdapter.submitList(uiState.wholePostItems)
                        }
                    }
                }

                launch {
                    viewModel.aroundPlaceItemsPagingData.collectLatest {
                        aroundPlaceListAdapter.submitData(it)
                    }
                }
            }
        }
    }
}