package com.shehuan.wanandroid.bean.navi

data class NaviBean(
    val name: String = "",
    val articles: List<NaviDataItem>?,
    val cid: Int = 0
)