package com.github.StormWyrm.wanandroid.utils

import android.content.Context
import android.content.SharedPreferences
import com.github.StormWyrm.wanandroid.App

object SharedPreferencesHelper {
    private val sharedPrefernces: SharedPreferences by lazy {
        App.getApp().getSharedPreferences("sp_wanandroid", Context.MODE_PRIVATE)
    }

    fun put(key: String, value: Any) = with(sharedPrefernces.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("The type of value is not support!")
        }.apply()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    fun <T> get(key: String, defaultValue: T): T = with(sharedPrefernces) {
        val value = when (defaultValue) {
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            else -> throw IllegalArgumentException("The type of value is not support!")
        }
        return value as T
    }

    fun contain(key: String): Boolean {
        return sharedPrefernces.contains(key)
    }

    fun remove(key: String) {
        sharedPrefernces.edit().remove(key).apply()
    }

    fun clear() {
        sharedPrefernces.edit().clear().apply()
    }
}