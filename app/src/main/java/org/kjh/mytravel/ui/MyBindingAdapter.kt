package org.kjh.mytravel.ui

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.kjh.mytravel.InputValidator.INPUT_TYPE_EMAIl
import org.kjh.mytravel.InputValidator.INPUT_TYPE_PW
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidateNickName
import org.kjh.mytravel.InputValidator.isValidatePw
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: MyBindingAdapter
 * Created by mac on 2021/12/22.
 *
 * Description:
 */

object MyBindingAdapter {

    @JvmStatic
    @BindingAdapter("imgResource")
    fun setImageDrawable(v: ImageView, imgResource: Int) {
        v.setImageResource(imgResource)
    }

    @JvmStatic
    @BindingAdapter("tint")
    fun setIconColorFilter(v: ImageView, colorCode: String) {
        v.setColorFilter(Color.parseColor(colorCode))
    }

    @JvmStatic
    @BindingAdapter("app:onFocusLost")
    fun TextInputEditText.onFocusLost(v: TextInputLayout) {
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                v.error          = null
                v.isErrorEnabled = false
            } else {
                val inputText = this.text.toString()
                v.error = when (this.tag) {
                    INPUT_TYPE_EMAIl -> isValidateEmail(inputText).errorMsg
                    INPUT_TYPE_PW    -> isValidatePw(inputText).errorMsg
                    else -> isValidateNickName(inputText).errorMsg
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:imgUri")
    fun bindImageWithUri(view: ImageView, imgUri: Uri?) {
        imgUri?.run {
            Glide.with(view)
                .load(imgUri)
//                .thumbnail(0.33f)
                .centerCrop()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imgUrl")
    fun bindImageWithUrl(view: ImageView, imgUrl: String?) {
        imgUrl?.run {
            Glide.with(view)
                .load(imgUrl)
                .thumbnail(0.33f)
                .centerCrop()
                .into(view)
        } ?: Glide.with(view)
            .load(R.drawable.ic_launcher_background)
            .thumbnail(0.33f)
            .centerCrop()
            .into(view)
    }
}