package com.github.StormWyrm.wanandroid.apis

import com.github.StormWyrm.wanandroid.utils.RetrofitHelper

object WanAndroidRetrofitHelper : RetrofitHelper<WanAndroidApis>() {
    override val baseUrl: String = "http://www.wanandroid.com/"
    override val clazz: Class<WanAndroidApis> = WanAndroidApis::class.java
}