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
    var filePath: String = "",
    var fileFormat: String = "",
    var height: Double = 0.0,
    var width: Double = 0.0,
    var photoDate: Long = 0,
    var photoRating: Int = 0,
    var fileSize: Long = 0
) : Parcelable