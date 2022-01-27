package com.simararora.bitapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.Lazy
import javax.inject.Inject

class ViewModelFactory<T : ViewModel> @Inject constructor(
    private val viewModel: Lazy<T>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel.get() as T

    inline fun <reified R : T> get(viewModelStoreOwner: ViewModelStoreOwner): T {
        return ViewModelProvider(viewModelStoreOwner, this).get(R::class.java)
    }
}
