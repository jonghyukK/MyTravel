package org.kjh.mytravel.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.kjh.data.EventHandler
import javax.inject.Inject

/**
 * MyTravel
 * Class: BaseActivity
 * Created by jonghyukkang on 2022/06/21.
 *
 * Description:
 */
abstract class BaseActivity<T: ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
): AppCompatActivity() {

    protected lateinit var binding: T

    @Inject
    lateinit var eventHandler: EventHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)

        initView()
        subscribeUi()
    }

    abstract fun initView()
    abstract fun subscribeUi()

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}