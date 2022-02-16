package org.kjh.mytravel.ui.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileEditBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.profile.ProfileEditFragmentArgs
import org.kjh.mytravel.ui.profile.ProfileEditViewModel
import org.kjh.mytravel.ui.profile.ProfileViewModel

const val NGINX_PATH = "http://192.168.219.102/images/"

@AndroidEntryPoint
class ProfileEditFragment
    : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val viewModel: ProfileEditViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val args: ProfileEditFragmentArgs by navArgs()

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext(), true)

            uriFilePath?.let {
                viewModel.updateProfileImg(uriFilePath)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.initProfileInfo(args.profileImg, args.nickName, args.introduce)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment = this

        initToolbarWithNavigation()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isSuccess && uiState.userItem != null) {
                        profileViewModel.updateMyProfile(uiState.userItem)
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    fun onClickEdit(v: View) {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    fun onClickSave(v: View) {
        var filePath = viewModel.uiState.value.profileImg

        if (filePath != null && filePath.startsWith(NGINX_PATH)) {
            filePath = filePath.replace(NGINX_PATH, "${requireContext().cacheDir.absolutePath}/")
        }

        viewModel.makeUpdateUserInfo(filePath)
    }

    private fun initToolbarWithNavigation() {
        binding.tbProfileEdit.setupWithNavController(findNavController())
    }
}