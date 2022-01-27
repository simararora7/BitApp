package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model

import androidx.annotation.StringRes
import androidx.annotation.StyleRes

data class TradingPairDetailItemUiModel(
    @StringRes val labelRes: Int,
    val value: String,
    @StyleRes val valueTextStyle: Int
)
