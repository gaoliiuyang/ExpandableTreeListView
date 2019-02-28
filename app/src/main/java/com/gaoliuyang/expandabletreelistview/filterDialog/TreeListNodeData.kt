package com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog

import com.gaoliuyang.expandabletreelistview.filterDialog.PersonalReportBean


/**
 * Created by gaoliuyang on 2018/12/12.
 */
class TreeListNodeData(var parentId: Int, var id: Int, var personalReportData: PersonalReportBean.Data?) {
    var level: Int = 0
}