package org.kjh.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.kjh.data.model.UserModel

/**
 * MyTravel
 * Class: UserDao
 * Created by jonghyukkang on 2022/08/22.
 *
 * Description:
 */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserModel)

    @Query("SELECT * FROM user")
    fun getUser(): Flow<UserModel>

    @Update
    suspend fun updateUser(user: UserModel)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}