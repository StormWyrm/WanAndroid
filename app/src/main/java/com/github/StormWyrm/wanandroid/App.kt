package com.github.StormWyrm.wanandroid

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import org.litepal.LitePalApplication

open class App : LitePalApplication() {
    private var refWatcher: RefWatcher? = null

    companion object {
        private lateinit var mContext: Context

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as App
            return myApplication.refWatcher
        }

        fun getApp(): Context = mContext
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        refWatcher = setupLeakCanary()
        initLogger()
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag("Wanandroid")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}