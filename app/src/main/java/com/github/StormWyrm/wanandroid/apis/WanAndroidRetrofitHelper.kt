package com.github.StormWyrm.wanandroid.apis

import com.github.StormWyrm.wanandroid.utils.RetrofitHelper

object WanAndroidRetrofitHelper : RetrofitHelper<WanAndroidApis>() {
    override val baseUrl: String = "https://www.wanandroid.com/"
    override fun getClazz(): Class<WanAndroidApis> = WanAndroidApis::class.java
}