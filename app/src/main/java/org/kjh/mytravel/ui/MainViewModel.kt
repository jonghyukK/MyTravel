package org.kjh.mytravel.ui

import androidx.lifecycle.ViewModel

/**
 * MyTravel
 * Class: MainViewModel
 * Created by mac on 2021/12/27.
 *
 * Description:
 */
class MainViewModel: ViewModel() {

    val tabBackStack = mutableListOf<Int>()

    fun addTabBackStack(back: Int) {
        if (tabBackStack.isNotEmpty()
            && tabBackStack.last() == back) return

        if (tabBackStack.contains(back)) {
            tabBackStack.removeAt(tabBackStack.indexOf(back))
        }

        tabBackStack.add(back)
    }

    fun deleteTabBackStack(back: Int) {
        tabBackStack.remove(back)
    }
}