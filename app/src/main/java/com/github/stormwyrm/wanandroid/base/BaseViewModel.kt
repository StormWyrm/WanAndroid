package com.github.stormwyrm.wanandroid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.api.ApiException
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {
    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    protected fun launch(
        block: suspend () -> Unit,
        error: (suspend (e: Exception) -> Unit)? = null,
        cancel: (suspend (e: Exception) -> Unit)? = null,
        showErrorToast: Boolean = true
    ): Job = viewModelScope.launch {
        try {
            block.invoke()
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> {
                    cancel?.invoke(e)
                }
                else -> {
                    onError(e, showErrorToast)
                    error?.invoke(e)
                }
            }
        }
    }

    protected fun <T> async(block: suspend () -> T): Deferred<T> = viewModelScope.async {
        block.invoke()
    }

    protected fun cancelJob(job: Job?) {
        job?.run {
            if (isActive && !isCompleted && !isCancelled) {
                cancel()
            }
        }
    }

    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -100 -> {
                        // TODO: 2020/8/14 登录过期
                    }
                    -1 -> {
                        if (showErrorToast) ToastUtils.showShort(e.message)
                    }
                    else -> {
                        if (showErrorToast) ToastUtils.showShort(e.message)
                    }
                }
            }
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException -> {
                if (showErrorToast) ToastUtils.showShort(R.string.network_request_failed)
            }

            is JsonParseException -> {
                if (showErrorToast) ToastUtils.showShort(R.string.api_data_parse_error)
            }
            else -> {
                if (showErrorToast) ToastUtils.showShort(e.message ?: return)
            }
        }
    }
}