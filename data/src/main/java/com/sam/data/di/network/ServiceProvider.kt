package com.sam.data.di.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.repository.MainRepository
import com.sam.data.retrofit.ServiceApi
import com.sam.data.util.Constants
import com.sam.data.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceProvider {

//    @Singleton
//    @Provides
//    fun provideGsonBuilder(): Gson {
//        return GsonBuilder()
//            .excludeFieldsWithoutExposeAnnotation()
//            .create()
//    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit.Builder =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit.Builder): ServiceApi =
        retrofit
            .build()
            .create(ServiceApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: ServiceApi): MainRepository = DefaultMainRepository(api)

    @Singleton
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