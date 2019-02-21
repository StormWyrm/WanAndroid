package com.github.StormWyrm.wanandroid

import android.annotation.SuppressLint
import android.content.res.Resources
import io.reactivex.Observable
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException

fun registerEventBus(obj: Any) {
    if (!EventBus.getDefault().isRegistered(obj))
        EventBus.getDefault().register(obj)
}

fun unregisterEventBus(obj: Any) {
    if (EventBus.getDefault().isRegistered(obj))
        EventBus.getDefault().register(obj)
}

@SuppressLint("CheckResult")
fun sendEvent(obj: Any) {
    EventBus.getDefault().post(obj)
    Observable.create<String> {
        val request = Request.Builder()
            .build()
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                it.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                it.onNext("")
                it.onComplete()
            }
        })
    }
}

fun dp2px(dpValue: Float): Int = (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()

fun px2dp(pxValue: Float): Int = (pxValue / Resources.getSystem().displayMetrics.density).toInt()