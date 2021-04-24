package com.sam.data.di

import com.sam.data.db.CharacterDao
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.repository.MainRepository
import com.sam.data.retrofit.ServiceApi
import com.sam.data.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRandomPageNumber() =
        Random(System.nanoTime()).nextInt(1, 34)

    @ViewModelScoped
    @Provides
    fun provideMainRepository(
        api: ServiceApi,
        characterDao: CharacterDao,
        randomPageNumber: Int
    ): MainRepository =
        DefaultMainRepository(api, characterDao, randomPageNumber)

    @ViewModelScoped
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}