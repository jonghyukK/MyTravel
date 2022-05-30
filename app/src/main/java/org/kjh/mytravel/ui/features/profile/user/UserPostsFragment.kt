package org.kjh.mytravel.ui.features.profile.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserPostsBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.ui.features.profile.PostMultipleViewAdapter
import org.kjh.mytravel.ui.features.profile.PostsGridItemDecoration
import org.kjh.mytravel.utils.navigateToPlaceDetail

/**
 * MyTravel
 * Class: UserPostsGridFragment
 * Created by jonghyukkang on 2022/05/30.
 *
 * Description:
 */
class UserPostsFragment
    : BaseFragment<FragmentUserPostsBinding>(R.layout.fragment_user_posts) {

    private var viewType = TYPE_GRID

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val viewModel: UserViewModel by viewModels({ requireParentFragment() })
    private val postMultipleViewAdapter by lazy {
        PostMultipleViewAdapter(
            viewType        = viewType,
            onClickPost     = { post -> navigateToPlaceDetail(post.placeName)},
            onClickBookmark = ::requestUpdateBookmark
        )
    }

    private fun requestUpdateBookmark(post: Post) {
        myProfileViewModel.updateBookmark(post.postId, post.placeName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewType = it.getInt(VIEW_TYPE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.gridPostsRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null

            if (viewType == TYPE_GRID) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                addItemDecoration(PostsGridItemDecoration(requireContext()))
            } else {
                layoutManager = LinearLayoutManager(requireContext())
            }

            adapter = postMultipleViewAdapter
        }
    }

    companion object {
        private const val VIEW_TYPE = "viewType"
        const val TYPE_GRID = 0
        const val TYPE_LINEAR = 1

        @JvmStatic
        fun newInstance(viewType: Int) =
            UserPostsFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIEW_TYPE, viewType)
                }
            }
    }
}