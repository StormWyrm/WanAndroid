package com.github.StormWyrm.wanandroid.base.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.registerEventBus
import com.github.StormWyrm.wanandroid.unregisterEventBus
import com.squareup.leakcanary.RefWatcher
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseActivity : AppCompatActivity() {
    private var refWatcher: RefWatcher? = null

    lateinit var mContext: Context
    lateinit var mActivity: BaseActivity

    open var isUseEventBus: Boolean = false
    open var hasEnterTranslateAnim = true
    open var hasExitTranslateAnim = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//保持竖屏

        mContext = applicationContext
        mActivity = this

        if (isUseEventBus)
            registerEventBus(this)

        refWatcher = App.getRefWatcher(this)

        initData()
        initLisenter()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isUseEventBus)
            unregisterEventBus(this)
        refWatcher?.watch(this)

    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        if (hasEnterTranslateAnim)
            overridePendingTransitionEnter()
    }

    override fun finish() {
        super.finish()
        if (hasExitTranslateAnim)
            overridePendingTransitionExit()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected open fun initData() {}

    protected open fun initLisenter() {}

    protected open fun initView() {}

    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left)
    }

    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right)
    }

    protected fun initToolbar(titleStr: String) {
        toolbar.run {
            title = titleStr
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}