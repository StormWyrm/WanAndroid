package com.github.StormWyrm.wanandroid.bean.navi

import android.os.Parcel
import android.os.Parcelable

data class NaviBean(
    val name: String = "",
    val articles: List<NaviDataItem>?,
    val cid: Int = 0
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.createTypedArrayList(NaviDataItem.CREATOR),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeTypedList(articles)
        writeInt(cid)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<NaviBean> = object : Parcelable.Creator<NaviBean> {
            override fun createFromParcel(source: Parcel): NaviBean = NaviBean(source)
            override fun newArray(size: Int): Array<NaviBean?> = arrayOfNulls(size)
        }
    }
}