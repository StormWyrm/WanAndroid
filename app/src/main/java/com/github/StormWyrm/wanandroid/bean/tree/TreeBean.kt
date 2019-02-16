package com.github.StormWyrm.wanandroid.bean.tree

data class TreeBean(
    val children: List<TreeDataItem>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

