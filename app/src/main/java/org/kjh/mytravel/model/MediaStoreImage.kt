package org.kjh.mytravel.model

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

/**
 * MyTravel
 * Class: MediaStoreImage
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */

data class MediaStoreImage(
    val id          : Long,
    val contentUri  : Uri,
    val realPath    : String,
    val bucketName  : String
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaStoreImage>() {
            override fun areItemsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ): Boolean =
                oldItem == newItem
        }
    }
}