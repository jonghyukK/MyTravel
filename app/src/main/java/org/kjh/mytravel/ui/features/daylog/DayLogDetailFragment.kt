package org.kjh.mytravel.ui.features.daylog

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentDaylogDetailBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.daylog.around.AroundPlaceListAdapter
import org.kjh.mytravel.utils.KakaoLinkUtils
import javax.inject.Inject

@AndroidEntryPoint
class DayLogDetailFragment
    : BaseFragment<FragmentDaylogDetailBinding>(R.layout.fragment_daylog_detail) {

    @Inject
    lateinit var dayLogDetailViewModelFactory: DayLogDetailViewModel.DayLogDetailAssistedFactory

    private val args: DayLogDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<DayLogDetailViewModel> {
        DayLogDetailViewModel.provideFactory(
            assistedFactory = dayLogDetailViewModelFactory,
            initPlaceName = args.placeName,
            initDayLogId  = args.dayLogId
        )
    }
    private val dayLogDetailAdapter by lazy {
        DayLogDetailAdapter(onClickShare = ::sendDayLogToKakaoLink)
    }
    private val aroundPlaceListAdapter by lazy { AroundPlaceListAdapter() }
    private val concatAdapter by lazy {
        ConcatAdapter(
            dayLogDetailAdapter,
            aroundPlaceListAdapter
        )
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.concatAdapter = concatAdapter
        binding.placeName = args.placeName
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        dayLogDetailAdapter.setUiItem(it)
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

    private fun sendDayLogToKakaoLink(dayLogItem: DayLog) {
        KakaoLinkUtils.sendDayLogKakaoLink(
            ctx = requireContext(),
            placeName = dayLogItem.placeName,
            content  = dayLogItem.content ?: "",
            imageUrl = dayLogItem.imageUrl[0]
        )
    }
}