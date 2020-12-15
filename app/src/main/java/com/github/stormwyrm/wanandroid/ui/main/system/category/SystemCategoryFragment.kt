package com.github.stormwyrm.wanandroid.ui.main.system.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ScreenUtils
import com.github.stormwyrm.wanandroid.App
import com.github.stormwyrm.wanandroid.R
import com.github.stormwyrm.wanandroid.bean.Category
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_system_category.*

class SystemCategoryFragment : BottomSheetDialogFragment() {
    private lateinit var systemCategoryAdapter: SystemCategoryAdapter
    private var height: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_system_category,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryList: List<Category> = arguments?.getParcelableArrayList(CATEGORY_LIST)!!

        systemCategoryAdapter = SystemCategoryAdapter(data = categoryList.toMutableList())

        recyclerView.adapter = systemCategoryAdapter
    }

    fun show(manager: FragmentManager, height: Int? = null) {
        this.height = height ?: (ScreenUtils.getAppScreenHeight() * 0.75f).toInt()
        if (!this.isAdded) {
            super.show(manager, "SystemCategoryFragment")
        }
    }

    companion object {
        const val CATEGORY_LIST = "categoryList"

        fun newInstance(
            categoryList: ArrayList<Category>
        ): SystemCategoryFragment {
            return SystemCategoryFragment().apply {
                arguments = Bundle().apply { putParcelableArrayList(CATEGORY_LIST, categoryList) }
            }
        }
    }
}