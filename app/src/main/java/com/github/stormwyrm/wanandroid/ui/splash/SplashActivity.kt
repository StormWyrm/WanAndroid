package com.github.stormwyrm.wanandroid.ui.splash

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.base.BaseActivity
import com.github.stormwyrm.wanandroid.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity(), CoroutineScope by MainScope() {

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        launch {
            ActivityUtils.startActivity(MainActivity::class.java)
            finish()
        }
    }
}