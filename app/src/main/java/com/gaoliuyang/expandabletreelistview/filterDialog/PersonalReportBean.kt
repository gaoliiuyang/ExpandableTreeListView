package com.gaoliuyang.expandabletreelistview.filterDialog

import android.support.annotation.Keep

/**
 * Created by gaoliuyang on 2018/12/19.
 */
@Keep
data class PersonalReportBean(
        val code: String,
        val data: List<Data>?,
        val message: String
) {
    @Keep
    data class Data(
            val title: String?,
            val type: Int,
            val desc: String?,
            val progress: Float,
            val evaluationDetailList: List<Data>?
    )
}
