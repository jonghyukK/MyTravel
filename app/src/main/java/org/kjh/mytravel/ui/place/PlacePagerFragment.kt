package org.kjh.mytravel.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlacePagerBinding
import org.kjh.mytravel.ui.base.BaseFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithAppBarLayout()
        initTabLayoutWithPager()
    }

    private fun initToolbarWithAppBarLayout() {
        with (binding) {
            tbPlacePagerToolbar.setupWithNavController(findNavController())
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