package org.kjh.mytravel.ui.features.place.infowithdaylog

import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceInfoWithDaylogBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.utils.onThrottleMenuItemClick
import javax.inject.Inject

@AndroidEntryPoint
class PlaceInfoWithDayLogFragment
    : BaseFragment<FragmentPlaceInfoWithDaylogBinding>(R.layout.fragment_place_info_with_daylog) {

    @Inject
    lateinit var placeInfoWithDayLogViewModelFactory: PlaceInfoWithDayLogViewModel.PlaceNameAssistedFactory

    private val args: PlaceInfoWithDayLogFragmentArgs by navArgs()
    private val viewModel by viewModels<PlaceInfoWithDayLogViewModel> {
        PlaceInfoWithDayLogViewModel.provideFactory(placeInfoWithDayLogViewModelFactory, args.placeName)
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.placeName = args.placeName

        binding.layoutPlaceInfoToolbar.tbToolBar.apply {
            inflateMenu(R.menu.menu_bookmark)
            onThrottleMenuItemClick { menu ->
                if (menu.itemId == R.id.bookmark) {
                    viewModel.requestBookmarkUpdate()
                }
            }
        }

        val tabLayout = binding.tlTabLayout
        val viewPager = binding.pager.apply {
            adapter = PlacePagerAdapter(this@PlaceInfoWithDayLogFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String? =
        when (position) {
            PLACE_DAY_LOG_PAGE_INDEX -> getString(R.string.daylog)
            PLACE_INFO_PAGE_INDEX -> getString(R.string.info)
            else -> null
        }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .map { it.isBookmarked }
                    .distinctUntilChanged()
                    .collect { isBookmarked ->
                        binding.layoutPlaceInfoToolbar.tbToolBar.menu[0]
                            .setIcon(changeBookmarkIconBy(isBookmarked))
                    }
            }
        }
    }

    private fun changeBookmarkIconBy(isBookmarked: Boolean) =
        if (isBookmarked) R.drawable.ic_rank else R.drawable.ic_bookmark
}