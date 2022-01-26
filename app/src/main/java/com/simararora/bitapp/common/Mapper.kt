package com.simararora.bitapp.common

abstract class Mapper<in I, out O> {
    abstract fun map(input: I): O

    fun mapList(inputList: List<I>): List<O> {
        return inputList.map(::map)
    }
}
