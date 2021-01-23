package com.example.niketest.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class List(
    val list:MutableList<Results>
):Parcelable