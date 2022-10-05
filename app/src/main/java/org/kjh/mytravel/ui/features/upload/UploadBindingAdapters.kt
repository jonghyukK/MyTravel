package org.kjh.mytravel.ui.features.upload

import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import org.kjh.mytravel.R
import org.kjh.mytravel.model.MapQueryItem

/**
 * MyTravel
 * Class: UploadBindingAdapters
 * Created by jonghyukkang on 2022/07/21.
 *
 * Description:
 */

@BindingAdapter("contentText")
fun bindContentWithHint(view: TextView, content: String) {
    view.text = content.ifBlank { view.resources.getString(R.string.input_content_your_experience)}
}

@BindingAdapter("convertPlaceAddress")
fun bindConvertedPlaceAddress(view: TextView, mapQueryItem: MapQueryItem?) {
    view.text = mapQueryItem?.let {
        val placeName = it.placeName
        val (cityName, subCityName) = it.placeAddress.split(" ")

        "$placeName | $cityName, $subCityName"
    } ?: view.resources.getString(R.string.input_place)
}

@BindingAdapter("showSelectedItems", "enableMotionAnim", "updateMotionAnimEnabled")
fun bindSelectedItemsMotion(
    motionLayout     : MotionLayout,
    showSelectedItems: Boolean,
    enableMotionAnim : Boolean,
    updateMotionAnimEnabled: (Boolean) -> Unit
) {
    val transitionId =
        if (showSelectedItems)
            R.id.cs_selected_items_height_end
        else
            R.id.cs_selected_items_height_start

    if (enableMotionAnim) {
        motionLayout.transitionToState(transitionId)
    } else {
        motionLayout.progress = 1f
        updateMotionAnimEnabled(true)
    }
}