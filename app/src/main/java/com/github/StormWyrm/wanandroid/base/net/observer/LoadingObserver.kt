package com.github.StormWyrm.wanandroid.base.net.observer

import android.app.ProgressDialog
import android.content.Context
import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseActivity
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class LoadingObserver<E>(
    context: Context,
    private val loadingMsg: Int = R.string.loading_msg,
    showLoading: Boolean = true,
    showErrorTip: Boolean = true
) : BaseObserver<E>(showErrorTip) {
    private val wrContext: WeakReference<Context> = WeakReference(context)

    private var loadingDialog: ProgressDialog? = null

    init {
        if (showLoading)
            loadingDialog = initLoading()
    }

    override fun onSubscribe(d: Disposable) {
        showLoading()
        super.onSubscribe(d)
    }

    override fun onError(e: Throwable) {
        dismissLoading()
        super.onError(e)
    }

    override fun onNext(data: E) {
        dismissLoading()
        super.onNext(data)
    }

    /**
     * 初始化loading
     */
    private fun initLoading() = ProgressDialog(wrContext.get() as BaseActivity).apply {
        setMessage((wrContext.get() as BaseActivity).getString(loadingMsg))
    }


    /**
     * 显示loading
     */
    private fun showLoading() {
        loadingDialog?.show()
    }

    /**
     * 取消loading
     */
    private fun dismissLoading() {
        loadingDialog?.dismiss()

        wrContext.clear()
    }
}