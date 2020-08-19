package com.github.stormwyrm.wanandroid.ui.splash

import com.blankj.utilcode.util.ActivityUtils
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseActivity
import com.github.stormwyrm.wanandroid.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity(), CoroutineScope by MainScope() {

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initView() {
        super.initView()
        launch {
            delay(3000L)
            ActivityUtils.startActivity(MainActivity::class.java)
            finish()
        }
    }
}