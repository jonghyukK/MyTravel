package org.kjh.mytravel.ui.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.domain.usecase.GetLocalImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: SelectPhotoViewModel
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

@HiltViewModel
class SelectPhotoViewModel @Inject constructor(
    private val getLocalImageUseCase: GetLocalImageUseCase
): ViewModel() {
    val mediaStoreImages = liveData {
        emit(getLocalImageUseCase())
    }
}