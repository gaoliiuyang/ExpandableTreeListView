package com.gaoliuyang.expandabletreelistview.filterDialog

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gaoliuyang.expandabletreelistview.R

/**
 * Created by gaoliuyang on 2018/12/25.
 */
class EvaluationResultAdapter(date: List<PersonalReportBean.Data>) :
    BaseQuickAdapter<PersonalReportBean.Data, BaseViewHolder>(
        R.layout.item_evaluation_result, date
    ) {

    private lateinit var onItemClickListener: OnItemClickListener

    override fun convert(helper: BaseViewHolder, item: PersonalReportBean.Data) {
        helper.setText(R.id.tv_report_title, item.title)
        helper.itemView.setOnClickListener { onItemClickListener.itemClick(item) }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun itemClick(item: PersonalReportBean.Data)
    }
}