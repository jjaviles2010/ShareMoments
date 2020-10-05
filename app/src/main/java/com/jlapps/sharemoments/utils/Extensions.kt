package com.jlapps.sharemoments.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val netDate = Date(this)
    return dateFormat.format(netDate)
}