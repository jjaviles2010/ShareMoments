package com.jlapps.sharemoments.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val netDate = Date(this)
    return dateFormat.format(netDate)
}

fun String.decodeToBitMap(width: Int = 200, height: Int = 200): Bitmap {
    val bmOptions = BitmapFactory.Options().apply {
        // Get the dimensions of the bitmap
        inJustDecodeBounds = true

        BitmapFactory.decodeFile(this@decodeToBitMap, this)

        val photoW: Int = outWidth
        val photoH: Int = outHeight

        // Determine how much to scale down the image
        val scaleFactor: Int = Math.max(1, Math.min(photoW / width, photoH / height))

        // Decode the image file into a Bitmap sized to fill the View
        inJustDecodeBounds = false
        inSampleSize = scaleFactor
    }
    return BitmapFactory.decodeFile(this, bmOptions)
}