package org.kjh.data.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * MyTravel
 * Class: FileUtils
 * Created by jonghyukkang on 2022/05/10.
 *
 * Description:
 */

object FileUtils {

    fun makeFormDataForUpload(url: String?): MultipartBody.Part =
        url?.let {
            val tempFile = File(it)
            val requestFile = tempFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        } ?: MultipartBody.Part.createFormData("empty", "empty")

    fun makeFormDataListForUpload(urlList: List<String>) =
        urlList.map { url -> makeFormDataForUpload(url) }
}