package com.simararora.bitapp.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AppDeps {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}
