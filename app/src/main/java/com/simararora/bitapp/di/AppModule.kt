package com.simararora.bitapp.di

import com.simararora.bitapp.common.DefaultSchedulersProvider
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.network.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun provideSchedulersProvider(
        defaultSchedulersProvider: DefaultSchedulersProvider
    ): SchedulersProvider = defaultSchedulersProvider
}
