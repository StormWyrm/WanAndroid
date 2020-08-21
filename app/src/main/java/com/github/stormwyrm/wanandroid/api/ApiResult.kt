package com.github.stormwyrm.wanandroid.api

data class ApiResult<T>(
    private val data: T,
    val errorCode: Int,
    val errorMsg: String
) {
    fun getData(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(errorCode, errorMsg)
        }
    }
}