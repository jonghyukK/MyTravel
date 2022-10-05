package org.kjh.mytravel.ui.features.place.infowithdaylog

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
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

        binding.tbPlacePagerToolbar.apply {
            setupWithNavController(findNavController())
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

    override fun subscribeUi() {}
}