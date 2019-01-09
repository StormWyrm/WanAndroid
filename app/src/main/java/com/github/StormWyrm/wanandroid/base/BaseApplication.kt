package com.github.StormWyrm.wanandroid.base

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class BaseApplication : Application() {
    private var refWatcher: RefWatcher? = null

    companion object {
        lateinit var mContext: Context
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as BaseApplication
            return myApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        refWatcher = setupLeakCanary()
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }
}