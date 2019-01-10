package com.github.StormWyrm.wanandroid.bean.project

data class ProjectCategoryBean(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

