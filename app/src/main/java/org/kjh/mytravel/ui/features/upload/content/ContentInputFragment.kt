package org.kjh.mytravel.ui.features.upload.content

import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentContentInputBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.upload.UploadViewModel
import org.kjh.mytravel.utils.onThrottleMenuItemClick

class ContentInputFragment
    : BaseFragment<FragmentContentInputBinding>(R.layout.fragment_content_input) {

    private val uploadViewModel : UploadViewModel by activityViewModels()

    override fun initView() {
        binding.uploadViewModel = uploadViewModel

        binding.tbContentInputToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_complete)
            onThrottleMenuItemClick { menu ->
                if (menu.itemId == R.id.complete) {
                    popBackStackAfterUpdateContent()
                }
            }
        }
    }

    private fun popBackStackAfterUpdateContent() {
        uploadViewModel.updateContent(binding.etContent.text.toString())
        findNavController().popBackStack()
    }

    override fun subscribeUi() {}
}