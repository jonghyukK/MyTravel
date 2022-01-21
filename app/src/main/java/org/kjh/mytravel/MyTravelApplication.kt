package org.kjh.mytravel

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
    }
}