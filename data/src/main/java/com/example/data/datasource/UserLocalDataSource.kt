package com.example.data.datasource

import com.example.data.db.UserDao
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: UserLocalDataSource
 * Created by jonghyukkang on 2022/02/04.
 *
 * Description:
 */
interface UserLocalDataSource {

    fun getMyProfile(): Flow<User?>

    suspend fun insertOrUpdateMyProfile(user: User)

    suspend fun deleteUser(email: String)
}

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
): UserLocalDataSource {

    override fun getMyProfile() = userDao.getMyProfile()

    override suspend fun insertOrUpdateMyProfile(user: User) =
        userDao.insertMyProfile(user)

    override suspend fun deleteUser(email: String) =
        userDao.deleteUser(email)

}