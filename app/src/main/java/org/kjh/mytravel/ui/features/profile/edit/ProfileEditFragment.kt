package org.kjh.mytravel.ui.features.profile.edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.features.profile.MyProfileViewModel
import org.kjh.mytravel.ui.base.BaseFragment
import javax.inject.Inject

const val NGINX_PATH = "http://192.168.219.102/images/"

interface ProfileEditClickEvent {
    fun onClickProfileImgEdit(v: View)
    fun onClickSave(v: View)
}

@AndroidEntryPoint
class ProfileEditFragment
    : BaseFragment<FragmentProfileEditBinding>(R.layout.fragment_profile_edit), ProfileEditClickEvent {

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

    override fun onClickProfileImgEdit(v: View) {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    override fun onClickSave(v: View) {
        var filePath = viewModel.profileImg.value

        if (filePath != null && filePath.startsWith(NGINX_PATH)) {
            filePath = filePath.replace(NGINX_PATH, "${requireContext().cacheDir.absolutePath}/")
        }

        viewModel.makeUpdateUserInfo(filePath)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this

        initView()
        observe()
    }

    private fun initView() {
        binding.tbProfileEditToolbar.setupWithNavController(findNavController())
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileUpdateState.collect { state ->
                    when (state) {
                        is UiState.Success -> handleSuccessCase(state.data)
                        is UiState.Error   -> handleErrorCase(state.errorMsg.res)
                    }
                }
            }
        }
    }

    private fun handleSuccessCase(userItem: User) {
        myProfileViewModel.updateMyProfile(profileItem = userItem)
        findNavController().popBackStack()
    }

    private fun handleErrorCase(errorMsg: Int) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        viewModel.shownErrorToast()
    }
}