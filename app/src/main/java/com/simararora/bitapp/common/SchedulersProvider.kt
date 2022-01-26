package com.simararora.bitapp.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

interface SchedulersProvider {
    val io: Scheduler
    val computation: Scheduler
    val ui: Scheduler
}

@Singleton
class DefaultSchedulersProvider @Inject constructor(): SchedulersProvider {

    override val io: Scheduler
        get() = Schedulers.io()

    override val computation: Scheduler
        get() = Schedulers.computation()

    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()
}
