package org.kjh.mytravel

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp

/**
 * MyTravel
 * Class: MyTravelApplication
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

@HiltAndroidApp
class MyTravelApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        KakaoSdk.init(this, "94cd3aaf873248b465d6ffbc3868aea5")
    }
}