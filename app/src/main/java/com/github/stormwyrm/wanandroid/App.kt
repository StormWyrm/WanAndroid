package com.github.stormwyrm.wanandroid

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.github.stormwyrm.wanandroid.common.loadmore.CommonLoadMoreView


class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        LoadMoreModuleConfig.defLoadMoreView = CommonLoadMoreView()
    }
}