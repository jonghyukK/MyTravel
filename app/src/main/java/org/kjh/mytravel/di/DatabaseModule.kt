package org.kjh.mytravel.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.kjh.data.dao.UserDao
import org.kjh.data.db.DATABASE_NAME
import org.kjh.data.db.UserDatabase
import javax.inject.Singleton

/**
 * MyTravel
 * Class: DatabaseModule
 * Created by jonghyukkang on 2022/08/22.
 *
 * Description:
 */

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries()
            .build()


    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao = userDatabase.userDao()
}