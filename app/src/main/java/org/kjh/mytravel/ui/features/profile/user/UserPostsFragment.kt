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
import org.kjh.mytravel.ui.features.profile.POSTS_GRID_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.PostMultipleViewAdapter
import org.kjh.mytravel.ui.features.profile.PostsGridItemDecoration
import org.kjh.mytravel.ui.features.profile.ViewType
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel

/**
 * MyTravel
 * Class: UserPostsGridFragment
 * Created by jonghyukkang on 2022/05/30.
 *
 * Description:
 */
class UserPostsFragment
    : BaseFragment<FragmentUserPostsBinding>(R.layout.fragment_user_posts) {

    private var viewType: ViewType = ViewType.Grid
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val viewModel: UserViewModel by viewModels({ requireParentFragment() })
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
        binding.viewModel = viewModel

        binding.postRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null
            adapter = postMultipleViewAdapter

            if (viewType == ViewType.Grid) {
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
            UserPostsFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIEW_TYPE, viewType)
                }
            }
    }
}