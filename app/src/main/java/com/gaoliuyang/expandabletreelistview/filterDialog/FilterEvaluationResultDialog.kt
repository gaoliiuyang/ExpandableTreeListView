package com.gaoliuyang.expandabletreelistview.filterDialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.*
import com.gaoliuyang.expandabletreelistview.R
import com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog.TreeListNodeData

/**
 * Created by gaoliuyang on 2018/12/14.
 */
class FilterEvaluationResultDialog : DialogFragment() {

    companion object {
        const val ALL_PERSONAL_REPORT_DATA_JSON = "all_personal_report_data_json"
    }

    private var sumCount = 0

    private lateinit var allPersonalReportDataJson: String
    private lateinit var personalReportDataList: ArrayList<PersonalReportBean.Data>

    private lateinit var mOnItemSelectedListener: OnItemSelectedListener

    fun newInstance(allPersonalReportDataJsonString: String): FilterEvaluationResultDialog {
        val dialog = FilterEvaluationResultDialog()
        val args = Bundle()
        args.putString(ALL_PERSONAL_REPORT_DATA_JSON, allPersonalReportDataJsonString)
        dialog.arguments = args
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            allPersonalReportDataJson = arguments!!.getString(ALL_PERSONAL_REPORT_DATA_JSON, "")
            if (TextUtils.isEmpty(allPersonalReportDataJson)) return
            personalReportDataList = GsonFormatter.fromJson(
                    allPersonalReportDataJson,
                    GsonFormatter.genericType<ArrayList<PersonalReportBean.Data>>()
            )
        }
    }

    @SuppressLint("RtlHardcoded")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.RIGHT)
        dialog.window?.setWindowAnimations(R.style.rightDialogAnim)
        return inflater.inflate(R.layout.dialog_filter_evaluation_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listView = view.findViewById<View>(R.id.ex_recycler) as ExpandableTreeListView
        val arrayList = ArrayList<TreeListNodeData>()
        arrayList.add(TreeListNodeData(-1, 0, null))
        personalReportDataList.forEach { data ->
            sumCount++
            val firstId = sumCount
            arrayList.add(TreeListNodeData(0, firstId, data))
            if (!data.evaluationDetailList.isNullOrEmpty()) {
                data.evaluationDetailList.forEach { secondData ->
                    sumCount++
                    val secondId = sumCount
                    arrayList.add(TreeListNodeData(firstId, secondId, secondData))
                    if (!secondData.evaluationDetailList.isNullOrEmpty()) {
                        secondData.evaluationDetailList.forEach { thirdData ->
                            sumCount++
                            arrayList.add(TreeListNodeData(secondId, sumCount, thirdData))
                        }
                    }
                }
            }
        }

        val adapter = object : ExpandableTreeAdapter(arrayList, activity) {
            override fun showRoot(): Boolean {
                return false
            }
        }
        listView.adapter = adapter
        listView.setOnExpandableListItemClickListener { treeNodeData ->
            mOnItemSelectedListener.selectedItem(treeNodeData.personalReportData!!)
            dismissAllowingStateLoss()
        }
    }

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener): FilterEvaluationResultDialog {
        mOnItemSelectedListener = onItemSelectedListener
        return this
    }

    interface OnItemSelectedListener {
        fun selectedItem(personalReportData: PersonalReportBean.Data)
    }
}