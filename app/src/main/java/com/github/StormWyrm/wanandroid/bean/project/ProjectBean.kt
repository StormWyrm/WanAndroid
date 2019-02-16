package com.github.StormWyrm.wanandroid.bean.project

data class ProjectBean(
    val curPage: Int,
    val datas: List<ProjectIDataItem>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)



