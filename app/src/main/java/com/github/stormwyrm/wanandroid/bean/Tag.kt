package com.github.stormwyrm.wanandroid.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    val name: String,
    val url: String
) : Parcelable
