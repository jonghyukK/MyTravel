package org.kjh.mytravel.ui

import android.graphics.Color
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.naver.maps.map.MapFragment
import org.kjh.mytravel.InputValidator
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
                v.isErrorEnabled = when (this.tag) {
                    INPUT_TYPE_EMAIl -> isValidateEmail(inputText) != InputValidator.EMAIL.VALIDATE
                    INPUT_TYPE_PW    -> isValidatePw(inputText) != InputValidator.PW.VALIDATE
                    else -> isValidateNickName(inputText) != InputValidator.NICKNAME.VALIDATE
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:textWithVisible")
    fun bindErrorTextWithVisible(v: TextView, value: String?) {
        v.text = value
        v.isVisible = value != null
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

    @JvmStatic
    @BindingAdapter("app:isVisible")
    fun bindVisible(view: ProgressBar, isVisible: Boolean) {
        view.isVisible = isVisible
    }
}