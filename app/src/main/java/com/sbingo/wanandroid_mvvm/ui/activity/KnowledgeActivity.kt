package com.sbingo.wanandroid_mvvm.ui.activity

import com.google.android.material.tabs.TabLayout
import com.sbingo.wanandroid_mvvm.Constants
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.adapter.KnowledgeViewPagerAdapter
import com.sbingo.wanandroid_mvvm.base.BaseActivity
import com.sbingo.wanandroid_mvvm.model.Chapter
import kotlinx.android.synthetic.main.fragment_tab_vp.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeActivity : BaseActivity() {

    private val knowledge by lazy {
        intent?.extras?.getSerializable(Constants.KNOWLEDGE) as Chapter
    }

    private val adapter by lazy {
        KnowledgeViewPagerAdapter(supportFragmentManager)
    }

    override var layoutId = R.layout.activity_knowledge_tree

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
        toolbar.run {
            title = knowledge.name
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        knowledge.let {
            adapter.setData(it.children)
            viewPager.offscreenPageLimit = it.children.size
        }
    }

    override fun subscribeUi() {
    }

}
