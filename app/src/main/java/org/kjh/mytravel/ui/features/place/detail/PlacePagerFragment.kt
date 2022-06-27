package org.kjh.mytravel.ui.features.place.detail

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlacePagerBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.utils.containPlace
import org.kjh.mytravel.utils.onThrottleMenuItemClick
import javax.inject.Inject

@AndroidEntryPoint
class PlacePagerFragment
    : BaseFragment<FragmentPlacePagerBinding>(R.layout.fragment_place_pager) {

    @Inject
    lateinit var placeViewModelFactory: PlaceViewModel.PlaceNameAssistedFactory

    private val args: PlacePagerFragmentArgs by navArgs()
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val viewModel by viewModels<PlaceViewModel> {
        PlaceViewModel.provideFactory(placeViewModelFactory, args.placeName)
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.placeName = args.placeName

        binding.tbPlacePagerToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_bookmark)
            onThrottleMenuItemClick { menu ->
                if (menu.itemId == R.id.bookmark) {
                    requestUpdateBookmark()
                }
            }
        }

        val tabLayout = binding.tlTabLayout
        val viewPager = binding.pager.apply {
            adapter = PlacePagerAdapter(this@PlacePagerFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    override fun subscribeUi() {
        myProfileViewModel.myProfileState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                val isBookmarked = uiState.myBookmarkItems.containPlace(args.placeName)
                viewModel.updateBookmarkState(isBookmarked)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun requestUpdateBookmark() {
        viewModel.uiState.value.placeItem?.let {
            myProfileViewModel.updateBookmark(it.posts[0].postId, it.placeName)
        }
    }

    private fun getTabTitle(position: Int): String? =
        when (position) {
            PLACE_DAY_LOG_PAGE_INDEX -> getString(R.string.daylog)
            PLACE_INFO_PAGE_INDEX -> getString(R.string.info)
            else -> null
        }


}