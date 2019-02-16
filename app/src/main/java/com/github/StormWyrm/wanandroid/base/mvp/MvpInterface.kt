package com.github.StormWyrm.wanandroid.base.mvp

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.exmple.corelib.state.IStateView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface ITopView {
    fun getContext(): Context?

    fun finishActivity()

    fun bindToPresenter()
}

interface ITopPresenter {
    fun attachView(view: ITopView)

    fun detachView()
}

interface ITopModel {
    fun addDisposable(disposable: Disposable)

    fun removeDisposable(disposable: Disposable)

    fun detach()
}

interface IView<P : ITopPresenter> : ITopView {
    var mPresenter: P

    override fun bindToPresenter() {
        mPresenter.attachView(this)
    }
}

interface IPresenter<V : ITopView, M : ITopModel> : ITopPresenter {
    var mView: V?
    var mModel: M?
    val isModelAttached: Boolean
        get() = mModel == null
    val isViewAttached: Boolean
        get() = mView == null

    override fun attachView(view: ITopView) {
        mView = view as V
    }

    override fun detachView() {
        mModel?.detach()
        mView = null
        mModel = null
    }

    fun checkViewAttached() {
        if (!isViewAttached)
            throw RuntimeException("the view isn't attach to presenter")
    }

    fun checkModelAttached() {
        if (!isModelAttached)
            throw RuntimeException("the model isn't attach to presenter")
    }
}

interface IModel : ITopModel {
    val mDisposablePool: CompositeDisposable

    override fun addDisposable(disposable: Disposable) {
        if (mDisposablePool.isDisposed)
            mDisposablePool.add(disposable)
    }

    override fun removeDisposable(disposable: Disposable) {
        if (mDisposablePool.isDisposed)
            mDisposablePool.remove(disposable)
    }

    override fun detach() {
        if (!mDisposablePool.isDisposed)
            mDisposablePool.clear()
    }
}

interface IListView<P : ITopPresenter> : IView<P> {
    val mRecyclerView: RecyclerView?
    val mStateView: IStateView?
    val mRefreshLayout: SmartRefreshLayout

    fun loadDataError()
    fun noData()
    fun loadMoreError()
    fun noMoreData()
}