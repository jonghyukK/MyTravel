package org.kjh.mytravel.ui.features.bookmark

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentBookMarkBinding
import org.kjh.mytravel.ui.base.BaseFragment

@AndroidEntryPoint
class BookMarkFragment
    : BaseFragment<FragmentBookMarkBinding>(R.layout.fragment_book_mark) {

    private val viewModel: BookmarkViewModel by viewModels()
    private val bookMarkListAdapter by lazy { BookmarkListAdapter() }

    override fun initView() {
        binding.viewModel = viewModel

        binding.bookmarksRecyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(BookmarkGridItemDecoration())
            adapter = bookMarkListAdapter
        }
    }

    override fun subscribeUi() {}
}