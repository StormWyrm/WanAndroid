package com.github.StormWyrm.wanandroid.bean.tree

import android.os.Parcel
import android.os.Parcelable

data class TreeBean(
    val children: List<TreeDataItem>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.createTypedArrayList(TreeDataItem.CREATOR),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        1 == source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(children)
        writeInt(courseId)
        writeInt(id)
        writeString(name)
        writeInt(order)
        writeInt(parentChapterId)
        writeInt((if (userControlSetTop) 1 else 0))
        writeInt(visible)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TreeBean> = object : Parcelable.Creator<TreeBean> {
            override fun createFromParcel(source: Parcel): TreeBean = TreeBean(source)
            override fun newArray(size: Int): Array<TreeBean?> = arrayOfNulls(size)
        }
    }
}

