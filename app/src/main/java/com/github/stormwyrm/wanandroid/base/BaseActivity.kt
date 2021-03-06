package com.github.stormwyrm.wanandroid.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils
import com.github.stormwyrm.wanandroid.common.dialog.ProgressDialogFragment

abstract class BaseActivity : AppCompatActivity() {
    protected val mActivity: BaseActivity by lazy{
        this
    }

    private val progressDialog: ProgressDialogFragment by lazy {
        ProgressDialogFragment.newInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.setNavBarLightMode(this, true)
        setContentView(getLayoutResId())
        initData(savedInstanceState)
        initView(savedInstanceState)
        initLisenter()
    }

    /**
     * 数据初始化相关
     */
    open fun initData(savedInstanceState: Bundle?) {
        // Override if need
    }


    /**
     * View初始化相关
     */
    open fun initView(savedInstanceState: Bundle?) {
        // Override if need
    }

    /**
     * 监听器初始化相关
     */
    open fun initLisenter() {

    }


    fun showProgressDialog(@StringRes messageId: Int) {
        if (progressDialog.isAdded) {
            progressDialog.show(supportFragmentManager, messageId, false)
        }
    }

    fun hideProgressDialog() {
        if (progressDialog.isVisible) {
            progressDialog.dismissAllowingStateLoss()
        }
    }

    abstract fun getLayoutResId(): Int
}