package com.example.domain.entity

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

/**
 * MyTravel
 * Class: MediaStoreImage
 * Created by mac on 2022/01/18.
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
            override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem == newItem
        }
    }
}