package com.github.StormWyrm.wanandroid.ui.chapter.detail

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.chapter.detail.ChapterDetailBean

class ChapterDetailPresenter : ChapterDetailContract.Presenter, BasePresenterKt<ChapterDetailContract.View>() {
    override var mModel: ChapterDetailContract.Model? = ChapterDetailModel()

    override fun requestChapterDetailArticle(chapterId: Int, pageNum: Int) {
        RequestManager.execute(this, mModel?.requestChapterDetailArticle(chapterId, pageNum),
            object : BaseObserver<ChapterDetailBean>() {
                override fun onSuccess(data: ChapterDetailBean) {
                    data.run {
                        if (datas.isNullOrEmpty()) {
                            if (pageNum == 0) {
                                mView?.noData()
                            } else {
                                mView?.noMoreData()
                            }
                        } else {
                            mView?.onRequestChapterDetailArticleSuccess(datas)
                        }
                        if (over) {
                            mView?.noMoreData()
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    if (pageNum == 0) {
                        mView?.loadDataError()
                    } else {
                        mView?.loadMoreError()
                    }
                }

            })
    }

}
