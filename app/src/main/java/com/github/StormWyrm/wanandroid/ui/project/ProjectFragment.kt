package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.fragment.BaseMvpFragment
import com.github.StormWyrm.wanandroid.bean.project.ProjectCategoryBean
import com.github.StormWyrm.wanandroid.ui.project.adapter.ProjectFragmentAdpter
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseMvpFragment<ProjectContract.View, ProjectContract.Presenter>(), ProjectContract.View {
    override var mPresenter: ProjectContract.Presenter = ProjectPresenter()

    private lateinit var categoryBeans: List<ProjectCategoryBean>

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initView() {
        super.initView()
        stateView.onRetry = {
            onRetry()
        }
    }

    override fun initLoad() {
        super.initLoad()
        onRetry()
    }

    private fun onRetry() {
        stateView.showLoading()
        mPresenter.requestProjectCategory()
    }

    override fun onRequestPorjectCategorySuccess(categoryBeans: List<ProjectCategoryBean>) {
        stateView.showSuccess()
        this.categoryBeans = categoryBeans
        vpProject.adapter = ProjectFragmentAdpter(categoryBeans, childFragmentManager)
        tabLayout.setupWithViewPager(vpProject)
    }

    override fun onRequestProjectCategoryError() {
        stateView.showError()
    }

    override fun onRequestProjectCategoryEmpty() {
        stateView.showEmpty()
    }


}