package com.simararora.bitapp.common

class SizeLimitLinkedHashMap<K, V>(
    private val maxSize: Int
) : LinkedHashMap<K, V>() {

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        return size > maxSize
    }
}
