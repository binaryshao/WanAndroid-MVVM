package com.sbingo.wanandroid_mvvm.adapter

import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.ui.fragment.WXArticleFragment

/**
 * Author: Sbingo666
 * Date:   2019/4/22
 */
class WeChatViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var fragments = mutableListOf<Fragment>()
    private var list = mutableListOf<Chapter>()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? =
        HtmlCompat.fromHtml(list[position].name, FROM_HTML_MODE_LEGACY).toString()

    fun setData(data: List<Chapter>) {
        list.run {
            clear()
            addAll(data)
            forEach {
                fragments.add(WXArticleFragment(it.id))
            }
        }
        notifyDataSetChanged()
    }
}