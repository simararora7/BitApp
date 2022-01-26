package com.simararora.bitapp

import androidx.multidex.MultiDexApplication
import com.simararora.bitapp.di.AppComponent
import com.simararora.bitapp.di.DaggerAppComponent

class BitAppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder().build()
    }

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }
}
