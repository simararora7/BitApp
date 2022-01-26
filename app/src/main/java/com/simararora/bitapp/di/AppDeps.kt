package com.simararora.bitapp.di

import com.simararora.bitapp.common.SchedulersProvider
import retrofit2.Retrofit

interface AppDeps {
    fun schedulersProvider(): SchedulersProvider
    fun retrofit(): Retrofit
}
