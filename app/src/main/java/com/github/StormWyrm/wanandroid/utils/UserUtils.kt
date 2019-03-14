package com.github.StormWyrm.wanandroid.utils

import com.github.StormWyrm.wanandroid.App
import com.github.StormWyrm.wanandroid.R

class UserUtils {
    companion object {
        fun saveUsername(username: String) {
            SharedPreferencesHelper.put("username", username)
        }

        fun getUsername(): String {
            return SharedPreferencesHelper.get("username", App.getApp().getString(R.string.login_title))
        }

        fun removeUsername() {
            SharedPreferencesHelper.remove("username")
        }

        fun saveCookie(cookie: String) {
            SharedPreferencesHelper.put("cookie", cookie)
        }

        fun getCookie(): String {
            return SharedPreferencesHelper.get("cookie", "")
        }

        fun removeCookie() {
            SharedPreferencesHelper.remove("cookie")
        }

    }
}