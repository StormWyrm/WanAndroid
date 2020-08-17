package com.github.stormwyrm.wanandroid.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.github.stormwyrm.wanandroid.common.dialog.ProgressDialogFragment

abstract class BaseActivity : AppCompatActivity() {

    private val progressDialog: ProgressDialogFragment by lazy {
        ProgressDialogFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
    }

    abstract fun getLayoutResId(): Int

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
}