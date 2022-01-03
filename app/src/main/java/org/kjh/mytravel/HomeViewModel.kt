package org.kjh.mytravel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * MyTravel
 * Class: HomeViewModel
 * Created by mac on 2021/12/31.
 *
 * Description:
 */
class HomeViewModel: ViewModel() {

    private val _eventList = MutableLiveData<List<EventItem>>()
    val eventList : LiveData<List<EventItem>> = _eventList

    fun settingEventList(list: List<EventItem>) {
        _eventList.value = list
    }
}