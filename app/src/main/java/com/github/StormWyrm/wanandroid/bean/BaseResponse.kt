package com.github.StormWyrm.wanandroid.bean

data class BaseResponse<T>(val errorCode: Int = 0,val errorMsg : String,val data : T)