package org.kjh.mytravel.ui.profile.upload

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kjh.mytravel.model.MediaStoreImage
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
    @ApplicationContext context: Context
): ViewModel() {
    private val contentResolver = context.contentResolver

    val mediaStoreImages = liveData {
        emit(getMediaStoreImages())
    }

    private suspend fun getMediaStoreImages(): List<MediaStoreImage> {

        val imageItems = mutableListOf<MediaStoreImage>()

        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                "_data",
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val absolutePathColumn = cursor.getColumnIndexOrThrow("_data")
                val bucketColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

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

                cursor.close()
            }
        }

        return imageItems
    }
}