package org.kjh.mytravel.ui.features.bookmark

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentBookMarkBinding
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel

@AndroidEntryPoint
class BookMarkFragment
    : BaseFragment<FragmentBookMarkBinding>(R.layout.fragment_book_mark) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val bookMarkListAdapter by lazy {
        BookmarkListAdapter(onClickBookmark = ::requestBookmarkStateUpdate)
    }

    private fun requestBookmarkStateUpdate(item: Bookmark) {
        myProfileViewModel.updateBookmark(item.postId, item.placeName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myProfileViewModel = myProfileViewModel

        binding.bookmarksRecyclerView.apply {
            setHasFixedSize(true)
            adapter = bookMarkListAdapter
        }
    }
}