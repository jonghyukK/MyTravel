package org.kjh.mytravel.ui.upload

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kjh.mytravel.data.model.PostUploadModel
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.usecase.UploadUseCase
import javax.inject.Inject

/**
 * MyTravel
 * Class: SelectPhotoViewModel
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

data class SelectPhotoUiState(
    val bucketName         : String = "전체보기",
    val mediaItemsInBucket : List<MediaStoreImage> = listOf(),
    val selectedItems      : List<MediaStoreImage> = listOf()
)

@HiltViewModel
class SelectPhotoViewModel @Inject constructor(
    private val application: Application,
    private val makeUploadUseCase: UploadUseCase
): ViewModel() {
    val content = MutableLiveData<String>()

    private var contentObserver: ContentObserver? = null
    private var _wholeMediaItemsWithBucket = mapOf<String, List<MediaStoreImage>>()

    private val _uiState = MutableStateFlow(SelectPhotoUiState())
    val uiState : StateFlow<SelectPhotoUiState> = _uiState

    init {
        getImagesFromMediaStore()
    }

    fun add(items: List<MediaStoreImage>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(selectedItems = items)
            }
        }
    }

    fun upload() {
        viewModelScope.launch {
            makeUploadUseCase
                .execute(
                    PostUploadModel(
                        email = "saz300@naver.com",
                        content = content.value.toString(),
                        cityName = "서울",
                        placeName = "잠실타워",
                        placeAddress = "서울시 성동구 잠실동 339-12"
                    )
                )
                .collect {
                    when (it) {
                        is Result.Success -> {
                            Logger.d("$it")
                        }
                    }
                }
        }
    }

    private fun getImagesFromMediaStore() {
        viewModelScope.launch {
            val mediaStoreImages = queryImages()

            _wholeMediaItemsWithBucket = mediaStoreImages

            _uiState.update {
                it.copy(
                    mediaItemsInBucket = mediaStoreImages[it.bucketName] ?: listOf()
                )
            }

            if (contentObserver == null) {
                contentObserver = application.contentResolver.registerObserver(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ){
                    getImagesFromMediaStore()
                }
            }
        }
    }

    private suspend fun queryImages(): Map<String, List<MediaStoreImage>> {
        var imagesWithBucket = mapOf<String, List<MediaStoreImage>>()
        val imageItems = mutableListOf<MediaStoreImage>()

        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                "_data",
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            application.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn           = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val absolutePathColumn = cursor.getColumnIndexOrThrow("_data")
                val bucketColumn       = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val realPath = cursor.getString(absolutePathColumn)
                    val bucketName = cursor.getString(bucketColumn)

                    val image = MediaStoreImage(
                        id,
                        contentUri,
                        realPath,
                        bucketName
                    )
                    imageItems += image
                }
            }

            imagesWithBucket = mapOf("전체보기" to imageItems) + imageItems.groupBy { it.bucketName }
        }

        return imagesWithBucket
    }

    override fun onCleared() {
        contentObserver?.let {
            application.contentResolver.unregisterContentObserver(it)
        }
    }
}

private fun ContentResolver.registerObserver(
    uri: Uri,
    observer: (selfChange: Boolean) -> Unit
): ContentObserver {
    val contentObserver = object: ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            observer(selfChange)
        }
    }
    registerContentObserver(uri, true, contentObserver)
    return contentObserver
}