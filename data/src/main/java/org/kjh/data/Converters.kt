package org.kjh.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import org.kjh.data.model.BookmarkModel
import org.kjh.data.model.DayLogModel

/**
 * MyTravel
 * Class: Converters
 * Created by jonghyukkang on 2022/08/22.
 *
 * Description:
 */
class Converters {

    @TypeConverter
    fun listToJson(value: List<DayLogModel>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<DayLogModel> {
        return Gson().fromJson(value, Array<DayLogModel>::class.java).toList()
    }

    @TypeConverter
    fun listToJsonBookmark(value: List<BookmarkModel>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToListBookmark(value: String): List<BookmarkModel> {
        return Gson().fromJson(value, Array<BookmarkModel>::class.java).toList()
    }
}