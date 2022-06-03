package org.kjh.mytravel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import org.kjh.mytravel.ui.GlobalErrorHandler
import javax.inject.Singleton

/**
 * MyTravel
 * Class: TestModule
 * Created by jonghyukkang on 2022/05/11.
 *
 * Description:
 */

@Module
@InstallIn(SingletonComponent::class)
object GlobalHandlerModule {

    @Singleton
    @Provides
    fun provideGlobalErrorHandler(
        coroutineScope: CoroutineScope
    ): GlobalErrorHandler = GlobalErrorHandler(coroutineScope)
}