package com.github.StormWyrm.wanandroid.bean.chapter

data class ChapterBean(
    val visible: Int = 0,
    val name: String = "",
    val userControlSetTop: Boolean = false,
    val id: Int = 0,
    val courseId: Int = 0,
    val parentChapterId: Int = 0,
    val order: Int = 0
)