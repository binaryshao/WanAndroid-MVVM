package com.sbingo.wanandroid_mvvm.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.adapter.ProjectViewPagerAdapter
import com.sbingo.wanandroid_mvvm.base.BaseFragment
import com.sbingo.wanandroid_mvvm.data.http.HttpManager
import com.sbingo.wanandroid_mvvm.repository.ProjectRepository
import com.sbingo.wanandroid_mvvm.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_tab_vp.*

/**
 * Author: Sbingo666
 * Date:   2019/4/15
 */
class ProjectFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = ProjectRepository(HttpManager.getInstance())
                return ProjectViewModel(repository) as T
            }
        })
            .get(ProjectViewModel::class.java)
    }

    private val adapter by lazy {
        ProjectViewPagerAdapter(childFragmentManager)
    }

    override var layoutId = R.layout.fragment_tab_vp

    override fun initData() {
        viewPager.adapter = adapter
        tabLayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        viewPager.setCurrentItem(it.position, false)
                    }
                }
            })
        }
    }

    override fun subscribeUi() {
        handleData(viewModel.projects) {
            adapter.setData(it)
        }
    }
}