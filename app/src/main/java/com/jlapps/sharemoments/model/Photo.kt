package com.jlapps.sharemoments.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "photo")
class Photo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idPhoto") var idPhoto: Int = 0,
    var title: String = "",
    var fileName: String = "",
    var filePath: String = "",
    var fileFormat: String = "JPEG",
    var height: Int = 0,
    var width: Int = 0,
    var photoDate: Long = 0,
    var photoRating: Float = 0.0f,
    var fileSize: Long = 0
) : Parcelable