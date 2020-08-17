package com.github.stormwyrm.wanandroid.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {
    private lateinit var mViewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        observe()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    abstract fun viewModelClass(): Class<VM>

    open fun observe() {
        // 登录失效，跳转登录页
        mViewModel.loginStatusInvalid.observe(viewLifecycleOwner, Observer {

        })
    }

}