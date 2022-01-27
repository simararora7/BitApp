package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di

import com.simararora.bitapp.BitAppApplication
import com.simararora.bitapp.di.AppDeps
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui.TradingPairDetailFragment
import dagger.Component

@TradingPairDetailScope
@Component(
    dependencies = [AppDeps::class],
    modules = [TradingPairDetailModule::class]
)
interface TradingPairDetailComponent {

    fun inject(tradingPairDetailFragment: TradingPairDetailFragment)

    @Component.Builder
    interface Builder {
        fun appDeps(appDeps: AppDeps): Builder
        fun build(): TradingPairDetailComponent
    }

    companion object {
        fun build() = DaggerTradingPairDetailComponent.builder()
            .appDeps(BitAppApplication.appComponent)
            .build()
    }
}
