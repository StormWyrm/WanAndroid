package com.github.StormWyrm.wanandroid.ui.chapter

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.chapter.ChapterBean

class ChapterPresenter : BasePresenterKt<ChapterContract.View>(), ChapterContract.Presenter {
    override var mModel: ChapterContract.Model? = ChapterModel()

    override fun requestChapterList() {
        RequestManager.execute(this,mModel?.requestChapterList(),
            object : BaseObserver<List<ChapterBean>>(){
                override fun onSuccess(data: List<ChapterBean>) {
                    data.run {
                        if(size == 0){
                            mView?.noData()
                        }else{
                            mView?.onRequestChapterListSuccess(data)
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    mView?.loadDataError()
                }

            })
    }
}