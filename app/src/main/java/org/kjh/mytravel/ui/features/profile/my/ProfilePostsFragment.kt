package org.kjh.mytravel.ui.features.profile.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfilePostsBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.POSTS_GRID_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.PostMultipleViewAdapter
import org.kjh.mytravel.ui.features.profile.PostsGridItemDecoration
import org.kjh.mytravel.ui.features.profile.ViewType


class ProfilePostsFragment
    : BaseFragment<FragmentProfilePostsBinding>(R.layout.fragment_profile_posts) {

    private var viewType: ViewType = ViewType.Grid
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val postMultipleViewAdapter by lazy {
        PostMultipleViewAdapter(
            viewType        = viewType,
            onClickBookmark = ::requestUpdateBookmark
        )
    }

    private fun requestUpdateBookmark(post: Post) {
        myProfileViewModel.updateBookmark(post.postId, post.placeName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewType = if (it.getInt(VIEW_TYPE) == POSTS_GRID_PAGE_INDEX) {
                ViewType.Grid
            } else {
                ViewType.Linear
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = myProfileViewModel

        binding.postRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null
            adapter = postMultipleViewAdapter

            if (viewType is ViewType.Grid) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                addItemDecoration(PostsGridItemDecoration(requireContext()))
            } else {
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    companion object {
        private const val VIEW_TYPE = "viewType"

        @JvmStatic
        fun newInstance(viewType: Int) =
            ProfilePostsFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIEW_TYPE, viewType)
                }
            }
    }
}