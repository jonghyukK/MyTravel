package org.kjh.mytravel.ui.features.place

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlacePagerBinding
import org.kjh.mytravel.ui.features.profile.MyProfileViewModel
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.utils.containPlace
import org.kjh.mytravel.utils.onThrottleClick
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.placeName = args.placeName

        initToolbarWithMenu()
        initTabLayoutWithPager()

        observeBookmarkState()
    }

    private fun initToolbarWithMenu() {
        binding.tbPlacePagerToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_bookmark)
            onThrottleMenuItemClick { menu ->
                if (menu.itemId == R.id.bookmark) {
                    requestUpdateBookmark()
                }
            }
        }
    }

    private fun requestUpdateBookmark() {
        viewModel.uiState.value.placeItem?.let {
            myProfileViewModel.updateBookmark(it.posts[0].postId, it.placeName)
        }
    }

    private fun initTabLayoutWithPager() {
        binding.pager.apply {
            adapter = PlacePagerAdapter(this@PlacePagerFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tlTabLayout, binding.pager) { tab, position ->
            tab.text = listOf("데이로그", "정보")[position]
        }.attach()
    }

    private fun observeBookmarkState() {
        myProfileViewModel.myProfileState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                val isBookmarked = uiState.myBookmarkItems.containPlace(args.placeName)
                viewModel.updateBookmarkState(isBookmarked)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}