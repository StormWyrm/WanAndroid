package com.github.stormwyrm.wanandroid.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.StringUtils
import com.github.stormwyrm.wanandroid.R
import kotlinx.android.synthetic.main.fragment_dialog_progress.*

class ProgressDialogFragment : DialogFragment() {
    private var messageResId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMsg.text = StringUtils.getString(messageResId ?: R.string.loading)
    }

    fun show(
        fragmentManager: FragmentManager,
        messageResId: Int? = null,
        isCancelable: Boolean = false
    ) {
        this.messageResId = messageResId
        this.isCancelable = isCancelable

        try {
            show(fragmentManager, this.javaClass.simpleName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        fun newInstance() = ProgressDialogFragment()
    }
}