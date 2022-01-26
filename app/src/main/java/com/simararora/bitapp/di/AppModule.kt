package com.simararora.bitapp.di

import com.simararora.bitapp.network.NetworkModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class
    ]
)
class AppModule
