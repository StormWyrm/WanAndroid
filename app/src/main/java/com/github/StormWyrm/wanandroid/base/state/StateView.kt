package com.exmple.corelib.state

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.github.StormWyrm.wanandroid.R
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import kotlinx.android.synthetic.main.layout_error_view.view.*
import kotlinx.android.synthetic.main.view_state_layout.view.*

/**
 *
 * @author  XQ Yang
 * @date 5/6/2018  12:23 PM
 */
class StateView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs), IStateView {
    var bindViewId = 0//绑定的id

    override var emptyMsg: String = "這里什么也没有噢!ヾ(･ω･`｡)"
    override var errorMsg: String = "怎么回事,出错了!(⊙_⊙)?"
    override var onStateChanged: ((Int) -> Unit)? = null
    override var curState: Int = STATE_EMPTY
    override var onRetry: (() -> Unit)? = null
    //绑定的view
    override var bindView: View? = null

    private val emptyLayout: View by lazy {
        val view = vs_empty.inflate()
        tv_empty.text = emptyMsg
        view
    }

    private val errorLayout: View by lazy {
        val view = vs_error.inflate()
        tv_error.text = errorMsg
        view
    }

    private val loadingLayout: View by lazy {
        val view = vs_loading.inflate()
        view
    }

    init {
        View.inflate(getContext(), R.layout.view_state_layout, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StateView)
        bindViewId = attributes.getResourceId(R.styleable.StateView_bindId, -1)
        attributes.recycle()
    }

    override fun showSuccess() {
        if (curState != STATE_SUCCESS) {
            hideAll()
            bindView?.visibility = View.VISIBLE
            curState = STATE_SUCCESS
            onStateChanged?.invoke(STATE_SUCCESS)
        }
    }

    override fun showError(msg: String?) {
        hideAll()
        msg?.let {
            tv_error.text = it
        }
        errorLayout.visibility = View.VISIBLE
        errorLayout.findViewById<Button>(R.id.btn_retry).setOnClickListener {
            onRetry?.invoke()
        }
        curState = STATE_ERROR
        onStateChanged?.invoke(STATE_ERROR)
    }

    override fun showEmpty(msg: String?) {
        hideAll()
        msg?.let {
            tv_empty.text = it
        }
        emptyLayout.visibility = View.VISIBLE
        curState = STATE_EMPTY
        onStateChanged?.invoke(STATE_EMPTY)
    }

    override fun showLoading() {
        hideAll()
        loadingLayout.visibility = View.VISIBLE
        curState = STATE_LOADING
        onStateChanged?.invoke(STATE_LOADING)
    }


    private fun hideAll() {
        if (bindView == null) {
            bindView = findViewById(bindViewId)
        }
        bindView?.visibility = View.GONE
        emptyLayout.visibility = View.GONE
        errorLayout.visibility = View.GONE
        loadingLayout.visibility = View.GONE
    }


}