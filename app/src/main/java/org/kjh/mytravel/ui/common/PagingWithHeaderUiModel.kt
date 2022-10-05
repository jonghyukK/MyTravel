package org.kjh.mytravel.ui.common

/**
 * MyTravel
 * Class: PagingWithHeaderUiModel
 * Created by jonghyukkang on 2022/10/04.
 *
 * Description:
 */

sealed class PagingWithHeaderUiModel<out T: Any> {
    data class PagingItem<out T: Any>(val item: T): PagingWithHeaderUiModel<T>()
    object HeaderItem: PagingWithHeaderUiModel<Nothing>()
}