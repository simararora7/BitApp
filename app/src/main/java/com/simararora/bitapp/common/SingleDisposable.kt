package com.simararora.bitapp.common

import io.reactivex.disposables.Disposable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SingleDisposable : ReadWriteProperty<Any, Disposable?> {
    private var disposable: Disposable? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): Disposable? = disposable

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Disposable?) {
        disposable?.dispose()
        disposable = value
    }
}
