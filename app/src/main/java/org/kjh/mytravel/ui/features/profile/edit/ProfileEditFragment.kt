package org.kjh.mytravel.ui.features.profile.edit

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileEditBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEditFragment
    : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit) {

    @Inject
    lateinit var profileEditViewModelFactory: ProfileEditViewModel.ProfileEditAssistedFactory
    private val args: ProfileEditFragmentArgs by navArgs()
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val viewModel: ProfileEditViewModel by viewModels {
        ProfileEditViewModel.provideFactory(
            profileEditViewModelFactory, args.profileImg, args.nickName, args.introduce)
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext(), true)

            uriFilePath?.let {
                viewModel.updateProfileImg(uriFilePath)
            }
        }
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment  = this

        binding.tbProfileEditToolbar.setupWithNavController(findNavController())
    }

    override fun subscribeUi() {
        viewModel.profileUpdateState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                if (state is UiState.Success) {
                    myProfileViewModel.updateMyProfile(profileItem = state.data)
                    findNavController().popBackStack()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun startCropImageActivity() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    fun cancelProfileEdit() {
        findNavController().popBackStack()
    }
}