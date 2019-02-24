package com.github.StormWyrm.wanandroid.base.activity

import com.github.StormWyrm.wanandroid.R

abstract class BaseTitleLoadActivity : BaseLoadActivity(){
    override fun getLayoutId(): Int {
        return R.layout.activity_base_title_load
    }
}