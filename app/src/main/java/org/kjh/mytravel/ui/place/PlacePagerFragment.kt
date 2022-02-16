package org.kjh.mytravel.ui.place

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlacePagerBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.profile.ProfileViewModel
import javax.inject.Inject

val TAB_TITLE = listOf("데이로그", "정보")

@AndroidEntryPoint
class PlacePagerFragment : BaseFragment<FragmentPlacePagerBinding>(R.layout.fragment_place_pager) {

    private val args: PlacePagerFragmentArgs by navArgs()

    @Inject
    lateinit var placeViewModelFactory: PlaceViewModel.PlaceNameAssistedFactory

    private val viewModel by viewModels<PlaceViewModel> {
        PlaceViewModel.provideFactory(placeViewModelFactory, args.placeName)
    }

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithAppBarLayout()
        initTabLayoutWithPager()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.uiState.collect { uiState ->
                    uiState.userItem?.let { user ->
                        val hasBookmark = user.bookMarks.find { it.placeName == args.placeName } != null
                       updateBookmarkIcon(hasBookmark)
                    }
                }
            }
        }
    }

    private fun updateBookmarkIcon(hasBookmark: Boolean) {
        with (binding.tbPlacePagerToolbar.menu.getItem(0)) {
            setIcon(
                if (hasBookmark) {
                R.drawable.ic_rank
            } else {
                R.drawable.ic_bookmark
            })
            isVisible = true
        }
    }


    private fun initToolbarWithAppBarLayout() {
        with (binding) {
            tbPlacePagerToolbar.setupWithNavController(findNavController())
            tbPlacePagerToolbar.inflateMenu(R.menu.menu_bookmark)
            tbPlacePagerToolbar.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.bookmark -> {
                        viewModel?.let {
                            it.uiState.value.placeItem?.let { placeItem ->
                                profileViewModel.updateMyBookmark(placeItem.posts[0])
                            }
                        }
                        true
                    }
                    else -> false
                }
            }

            var scrollRange = -1
            abl.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = barLayout?.totalScrollRange!!
                }

                ctl.title = if (scrollRange + verticalOffset == 0) args.placeName else " "
            })
        }
    }

    private fun initTabLayoutWithPager() {
        binding.pager.apply {
            adapter = PlacePagerAdapter(this@PlacePagerFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tlTabLayout, binding.pager) { tab, position ->
            tab.text = TAB_TITLE[position]
        }.attach()
    }
}