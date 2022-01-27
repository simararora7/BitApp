package com.simararora.bitapp.features.tradingpairs.tradingpairlist.di

import com.simararora.bitapp.BitAppApplication
import com.simararora.bitapp.di.AppDeps
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.ui.TradingPairListFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppDeps::class],
    modules = [TradingPairListModule::class]
)
@TradingPairListScope
interface TradingPairListComponent {
    fun inject(tradingPairListFragment: TradingPairListFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun tradingPairListFragment(tradingPairListFragment: TradingPairListFragment): Builder

        fun appDeps(appComponent: AppDeps): Builder
        fun build(): TradingPairListComponent
    }

    companion object {
        fun build(tradingPairListFragment: TradingPairListFragment) =
            DaggerTradingPairListComponent.builder()
                .tradingPairListFragment(tradingPairListFragment)
                .appDeps(BitAppApplication.appComponent)
                .build()
    }
}
