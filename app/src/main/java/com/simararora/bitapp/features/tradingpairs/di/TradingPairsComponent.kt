package com.simararora.bitapp.features.tradingpairs.di

import com.simararora.bitapp.BitAppApplication
import com.simararora.bitapp.di.AppDeps
import com.simararora.bitapp.features.tradingpairs.ui.TradingPairsFragment
import dagger.Component

@Component(
    dependencies = [AppDeps::class],
    modules = [TradingPairsModule::class]
)
@TradingPairsScope
interface TradingPairsComponent {
    fun inject(tradingPairsFragment: TradingPairsFragment)

    @Component.Builder
    interface Builder {
        fun appDeps(appComponent: AppDeps): Builder
        fun build(): TradingPairsComponent
    }

    companion object {
        fun build() =
            DaggerTradingPairsComponent.builder()
                .appDeps(BitAppApplication.appComponent)
                .build()

    }
}
