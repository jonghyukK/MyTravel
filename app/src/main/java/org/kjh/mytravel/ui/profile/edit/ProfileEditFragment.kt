package org.kjh.mytravel.ui.profile.edit

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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
import org.kjh.mytravel.MyProfileViewModel
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileEditBinding
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseFragment
import javax.inject.Inject

const val NGINX_PATH = "http://192.168.219.102/images/"

@AndroidEntryPoint
class ProfileEditFragment
    : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    private val args: ProfileEditFragmentArgs by navArgs()

    @Inject
    lateinit var profileEditViewModelFactory: ProfileEditViewModel.ProfileEditAssistedFactory

    private val viewModel: ProfileEditViewModel by viewModels {
        ProfileEditViewModel.provideFactory(profileEditViewModelFactory,
            args.profileImg, args.nickName, args.introduce)
    }

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext(), true)

            uriFilePath?.let {
                viewModel.updateProfileImg(uriFilePath)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this
        binding.myProfileViewModel = myProfileViewModel

        initToolbarWithNavigation()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileUpdateState.collect { state ->
                    binding.pbLoading.isVisible = state is ProfileUpdateState.Loading
                    when (state) {
                        is ProfileUpdateState.Success -> handleSuccessCase(state.userItem)
                        is ProfileUpdateState.Error   -> handleErrorCase(state.error)
                    }
                }
            }
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbProfileEditToolbar.setupWithNavController(findNavController())
    }

    private fun handleSuccessCase(userItem: User) {
        myProfileViewModel.updateMyProfile(profileItem = userItem)
        findNavController().popBackStack()
    }

    private fun handleErrorCase(error: String?) {
        error?.let {
            showError(it)
            viewModel.shownErrorToast()
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
        var filePath = viewModel.profileImg.value

        if (filePath != null && filePath.startsWith(NGINX_PATH)) {
            filePath = filePath.replace(NGINX_PATH, "${requireContext().cacheDir.absolutePath}/")
        }

        viewModel.makeUpdateUserInfo(filePath)
    }
}