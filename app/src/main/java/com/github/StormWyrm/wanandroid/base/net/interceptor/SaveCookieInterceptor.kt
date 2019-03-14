package com.github.StormWyrm.wanandroid.base.net.interceptor

import com.github.StormWyrm.wanandroid.utils.UserUtils
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val requestUrl = request.url().toString()
        if (requestUrl.contains("register") || requestUrl.contains("login")) {
            val cookies = response.headers("Set-Cookie")
            if (!cookies.isNullOrEmpty()) {
                val cookieStr = StringBuilder()
                for (cookie in cookies) {
                    cookieStr.append(cookie)
                    cookieStr.append("#")
                }
                UserUtils.saveCookie(cookieStr.toString())
            }
        }
        return response
    }
}