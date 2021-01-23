package com.example.niketest.models

import android.graphics.Color
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "lastWordSearched")
@Parcelize
data class Results(
    @ColumnInfo(name = "definition")
    val definition: String?,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "word")
    val word:String?,
    @ColumnInfo(name = "thumbs_up")
    val thumbs_up:Int?,
    @ColumnInfo(name = "thumbs_down")
    val thumbs_down:Int?,
    @ColumnInfo(name = "example")
    val example:String?
):Parcelable
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}