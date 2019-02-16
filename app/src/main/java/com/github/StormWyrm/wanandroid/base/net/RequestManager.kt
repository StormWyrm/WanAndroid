package com.github.StormWyrm.wanandroid.base.net

import com.exmple.corelib.scheduler.IoMainScheduler
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.net.convert.ExceptionConvert
import com.github.StormWyrm.wanandroid.base.net.convert.ResponseConvert
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import io.reactivex.Observable

object RequestManager {
    fun <T> execute(presenter: IPresenter<*, *>, observable: Observable<BaseResponse<T>>?, observer: BaseObserver<T>) {
        if (observable == null)
            throw RuntimeException("the observable is null")

        observable
            .map(ResponseConvert())
            .onErrorResumeNext(ExceptionConvert<T>())
            .compose(IoMainScheduler())
            .subscribe(observer)

        presenter.mModel?.addDisposable(observer.getDisposable())
    }
}