package org.kjh.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.kjh.data.dao.UserDao
import org.kjh.data.model.BookmarkModel
import org.kjh.data.model.UserModel

/**
 * MyTravel
 * Class: UserLocalDataSource
 * Created by jonghyukkang on 2022/08/22.
 *
 * Description:
 */

interface UserLocalDataSource {
    suspend fun saveUser(user: UserModel)
    suspend fun getUser(): Flow<UserModel>
    suspend fun getUserOrNull(): Flow<UserModel?>
    suspend fun deleteAll()
    suspend fun updateUser(user: UserModel)
}

class UserLocalDataSourceImpl(
    private val userDao: UserDao
): UserLocalDataSource {

    override suspend fun saveUser(user: UserModel) {
        userDao.saveUser(user)
    }

    override suspend fun getUser(): Flow<UserModel> {
        return userDao.getUser()
    }

    override suspend fun getUserOrNull(): Flow<UserModel?> {
        return userDao.getUser()
    }

    override suspend fun deleteAll() {
        userDao.deleteAll()
    }

    override suspend fun updateUser(user: UserModel) {
        userDao.updateUser(user)
    }
}