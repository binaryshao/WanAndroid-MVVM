package com.sbingo.wanandroid_mvvm.adapter

import android.content.Intent
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.DiffUtil
import com.sbingo.wanandroid_mvvm.Constants
import com.sbingo.wanandroid_mvvm.R
import com.sbingo.wanandroid_mvvm.base.paging.BasePagingAdapter
import com.sbingo.wanandroid_mvvm.model.Chapter
import com.sbingo.wanandroid_mvvm.ui.activity.KnowledgeActivity

/**
 * Author: Sbingo666
 * Date:   2019/4/26
 */
class KnowledgeTreeAdapter(retryCallback: () -> Unit) : BasePagingAdapter<Chapter>(diffCallback, retryCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Chapter>() {
            override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun getItemLayout() = R.layout.knowledge_tree_item

    override fun bind(holder: ViewHolder, item: Chapter, position: Int) {
        holder.run {
            setText(R.id.title, item.name)
            setText(
                R.id.subtitle,
                item.children.joinToString(
                    "     ",
                    transform = { child -> HtmlCompat.fromHtml(child.name, FROM_HTML_MODE_LEGACY).toString() })
            )
        }
        holder.itemView.setOnClickListener {
            Intent(context, KnowledgeActivity::class.java).run {
                putExtra(Constants.KNOWLEDGE, item)
                context.startActivity(this)
            }
        }
    }
}