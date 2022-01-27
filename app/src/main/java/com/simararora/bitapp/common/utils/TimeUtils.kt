package com.simararora.bitapp.common.utils

import java.text.SimpleDateFormat
import java.util.*

enum class TimeFormat(
    val formatString: String
) {
    HH_MM_SS_AA("hh:mm:ss aa")
}

fun formatTime(timeFormat: TimeFormat, epoch: Long): String {
    return SimpleDateFormat(
        timeFormat.formatString,
        Locale.getDefault()
    ).format(Date(epoch))
}
