package com.github.StormWyrm.wanandroid.base.net.convert

import com.github.StormWyrm.wanandroid.base.exception.ApiException
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import io.reactivex.functions.Function

class ResponseConvert<T> : Function<BaseResponse<T>, T> {
    /**
     * Apply some calculation to the input value and return some other value.
     * @param t the input value
     * @return the output value
     * @throws Exception on error
     */
    override fun apply(t: BaseResponse<T>): T {
        if (t.errorCode != 0) {
            throw ApiException(t.errorCode, t.errorMsg)
        }
        var data = t.data
        if(t.data == null){
            data = "" as T
        }
        return data
    }
}