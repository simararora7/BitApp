package com.simararora.bitapp.common

sealed class ViewState<T> {

    data class Loading<T>(val cachedData: T? = null) : ViewState<T>()

    data class Error<T>(val throwable: Throwable) : ViewState<T>()

    data class Success<T>(val data: T) : ViewState<T>()
}
