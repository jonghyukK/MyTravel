package com.example.data.db

import androidx.room.TypeConverter
import com.example.domain.entity.Post
import com.google.gson.Gson

/**
 * MyTravel
 * Class: StringConverters
 * Created by jonghyukkang on 2022/02/04.
 *
 * Description:
 */
class StringConverters {

    @TypeConverter
    fun listToJson(value: List<String>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}

class PostConverters {

    @TypeConverter
    fun listToJson(value: List<Post>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Post>::class.java).toList()
}