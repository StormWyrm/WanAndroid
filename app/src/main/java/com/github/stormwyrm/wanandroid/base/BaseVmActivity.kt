package com.github.stormwyrm.wanandroid.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {
    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initViewModel()
        observe()
        super.onCreate(savedInstanceState)
    }

    protected abstract fun viewModelClass(): Class<VM>

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    open fun observe() {
        mViewModel.loginStatusInvalid.observe(this, Observer<Boolean> {

        })
    }

}