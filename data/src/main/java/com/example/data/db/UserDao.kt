package com.example.data.db

import androidx.room.*
import com.example.domain.entity.Post
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: UserDao
 * Created by jonghyukkang on 2022/02/04.
 *
 * Description:
 */
@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM User")
    fun getMyProfile(): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyProfile(user: User)

    @Query("DELETE FROM User WHERE email = :email")
    suspend fun deleteUser(email: String)
}