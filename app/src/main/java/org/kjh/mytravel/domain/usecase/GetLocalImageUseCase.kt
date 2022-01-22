package org.kjh.mytravel.domain.usecase

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kjh.mytravel.ui.upload.MediaStoreImage
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetLocalImageUseCase
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */
class GetLocalImageUseCase @Inject constructor(
    @ApplicationContext context: Context
){
    private val contentResolver = context.contentResolver

    suspend operator fun invoke(): List<MediaStoreImage> {
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

                cursor.close()
            }
        }

        return imageItems
    }
}