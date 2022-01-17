package org.kjh.mytravel

import android.app.Application

/**
 * MyTravel
 * Class: MyTravelApplication
 * Created by mac on 2022/01/15.
 *
 * Description:
 */
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
    }

    fun getDataStore(): DataStoreManager = dataStore
}