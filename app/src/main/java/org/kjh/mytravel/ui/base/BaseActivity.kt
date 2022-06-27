package org.kjh.mytravel.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import org.kjh.mytravel.ui.GlobalErrorHandler
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
    lateinit var globalErrorHandler: GlobalErrorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)

        initView()
        subscribeUi()

        subscribeGlobalError()
    }

    abstract fun initView()
    abstract fun subscribeUi()

    // (추후 각 화면별 에러 처리 필요).
    private fun subscribeGlobalError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                globalErrorHandler.errorEvent.collect {
                    showToast(it)
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}