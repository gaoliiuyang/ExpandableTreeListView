package com.gaoliuyang.expandabletreelistview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gaoliuyang.expandabletreelistview.filterDialog.EvaluationResultAdapter
import com.gaoliuyang.expandabletreelistview.filterDialog.FilterEvaluationResultDialog
import com.gaoliuyang.expandabletreelistview.filterDialog.PersonalReportBean
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var ivFilter: ImageView

    private lateinit var tvEvaluation: TextView
    private lateinit var tvDesc: TextView
    private lateinit var divider: View
    private lateinit var llChild: LinearLayout
    private lateinit var rvChild: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        tvTitle = findViewById(R.id.tv_title)
        ivFilter = findViewById(R.id.iv_filter)

        tvEvaluation = findViewById(R.id.tv_evaluation)
        tvDesc = findViewById(R.id.tv_desc)
        divider = findViewById(R.id.divider)
        llChild = findViewById(R.id.ll_child)
        rvChild = findViewById(R.id.rv_child)

        rvChild.layoutManager = LinearLayoutManager(this)
        rvChild.setHasFixedSize(true)
        rvChild.isNestedScrollingEnabled = false

        val date: List<PersonalReportBean.Data> = mutableListOf(
            PersonalReportBean.Data(
                "第一级1",
                1,
                "第一级1第一级1第一级1第一级1第一级1",
                0.59f,
                mutableListOf(
                    PersonalReportBean.Data(
                        "第二级1",
                        1,
                        "第二级1第二级1第二级1第二级1第二级1",
                        0.33f,
                        mutableListOf(
                            PersonalReportBean.Data(
                                "第三级1",
                                2,
                                "第三级1第三级1第三级1第三级1第三级1",
                                0.65f,
                                mutableListOf()
                            ),
                            PersonalReportBean.Data(
                                "第三级2",
                                4,
                                "第三级2第三级2第三级2第三级2第三级2",
                                0.88f,
                                mutableListOf()
                            )
                        )
                    ),
                    PersonalReportBean.Data(
                        "第二级2",
                        2,
                        "第二级2第二级2第二级2第二级2第二级2",
                        0.5f,
                        mutableListOf(
                            PersonalReportBean.Data(
                                "第三级3",
                                2,
                                "第三级3第三级3第三级3第三级3第三级3",
                                0.65f,
                                mutableListOf()
                            ),
                            PersonalReportBean.Data(
                                "第三级4",
                                4,
                                "第三级4第三级4第三级4第三级4第三级4",
                                0.88f,
                                mutableListOf()
                            )
                        )
                    ),
                    PersonalReportBean.Data(
                        "第二级3",
                        3,
                        "第二级3第二级3第二级3第二级3第二级3",
                        0.7f,
                        mutableListOf()
                    )
                )

            ),
            PersonalReportBean.Data(
                "第一级2",
                5,
                "第一级2第一级2第一级2第一级2第一级2",
                0.98f,
                mutableListOf(
                )

            ),
            PersonalReportBean.Data(
                "第一级3",
                3,
                "第一级3第一级3第一级3第一级3第一级3",
                0.46f,
                mutableListOf(
                    PersonalReportBean.Data(
                        "第二级5",
                        1,
                        "第二级5第二级5第二级5第二级5第二级5",
                        0.33f,
                        mutableListOf()
                    ),
                    PersonalReportBean.Data(
                        "第二级6",
                        3,
                        "第二级6第二级6第二级6第二级6第二级6",
                        0.77f,
                        mutableListOf()
                    )
                )

            )
        )

        ivFilter.setOnClickListener {
            FilterEvaluationResultDialog().newInstance(Gson().toJson(date))
                .setOnItemSelectedListener(object : FilterEvaluationResultDialog.OnItemSelectedListener {
                    override fun selectedItem(personalReportData: PersonalReportBean.Data) {
                        setPersonalReportData(personalReportData)
                        tvTitle.text = personalReportData.title!!
                    }
                }).show(supportFragmentManager, "")
        }
    }

    private fun setPersonalReportData(personalReportData: PersonalReportBean.Data) {
        when (personalReportData.type) {
            1 -> tvEvaluation.text = getString(R.string.evaluation_one)
            2 -> tvEvaluation.text = getString(R.string.evaluation_two)
            3 -> tvEvaluation.text = getString(R.string.evaluation_three)
            4 -> tvEvaluation.text = getString(R.string.evaluation_four)
            5 -> tvEvaluation.text = getString(R.string.evaluation_five)
        }
        if (TextUtils.isEmpty(personalReportData.desc)) {
            tvDesc.visibility = View.GONE
        } else {
            tvDesc.visibility = View.VISIBLE
            tvDesc.text = personalReportData.desc
        }
        if (personalReportData.evaluationDetailList.isNullOrEmpty()) {
            divider.visibility = View.GONE
            llChild.visibility = View.GONE
        } else {
            divider.visibility = View.VISIBLE
            llChild.visibility = View.VISIBLE
            val adapter = EvaluationResultAdapter(personalReportData.evaluationDetailList)
            rvChild.adapter = adapter
            adapter.setOnItemClickListener(object : EvaluationResultAdapter.OnItemClickListener {
                override fun itemClick(item: PersonalReportBean.Data) {
                    setPersonalReportData(item)
                    tvTitle.text = item.title!!
                }

            })

        }
    }
}
