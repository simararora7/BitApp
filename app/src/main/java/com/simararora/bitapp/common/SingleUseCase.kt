package com.simararora.bitapp.common

import io.reactivex.Single

interface SingleUseCase<Param, Result> {
    fun execute(param: Param): Single<Result>
}
