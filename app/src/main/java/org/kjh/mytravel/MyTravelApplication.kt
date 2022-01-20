package org.kjh.mytravel

import android.app.Application
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

    private lateinit var dataStore: DataStoreManager

    companion object {
        private lateinit var myTravelApplication: MyTravelApplication
        fun getInstance(): MyTravelApplication = myTravelApplication
    }

    override fun onCreate() {
        super.onCreate()
        myTravelApplication = this
        dataStore = DataStoreManager(this)

        Logger.addLogAdapter(AndroidLogAdapter())
    }

    fun getDataStore(): DataStoreManager = dataStore
}