package org.kjh.mytravel.di

import javax.inject.Qualifier

/**
 * MyTravel
 * Class: CoroutinesQualifiers
 * Created by jonghyukkang on 2022/06/21.
 *
 * Description:
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher