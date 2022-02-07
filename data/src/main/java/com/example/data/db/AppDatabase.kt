package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.domain.entity.Post
import com.example.domain.entity.User

/**
 * MyTravel
 * Class: AppDatabase
 * Created by jonghyukkang on 2022/02/04.
 *
 * Description:
 */

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(StringConverters::class, PostConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}