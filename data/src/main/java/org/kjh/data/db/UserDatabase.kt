package org.kjh.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.kjh.data.Converters
import org.kjh.data.dao.UserDao
import org.kjh.data.model.UserModel

/**
 * MyTravel
 * Class: UserDatabase
 * Created by jonghyukkang on 2022/08/22.
 *
 * Description:
 */

const val DATABASE_NAME = "user.db"

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}