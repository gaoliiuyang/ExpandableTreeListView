package com.gaoliuyang.expandabletreelistview.filterDialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog.TreeListNodeData;
import com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog.TreeNode;


/**
 * 可折叠展开的listView
 */
public class ExpandableTreeListView extends ListView {
    private ExpandableTreeAdapter expandableTreeAdapter;
    private OnExpandableListItemClickListener onExpandableListItemClickListener;

    public ExpandableTreeListView(Context context) {
        super(context);
        initView();
    }

    public ExpandableTreeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOnItemClickListener((parent, view, position, id) -> {
            if (expandableTreeAdapter == null) {
                return;
            }
            update(position);
        });
    }

    private void update(int position) {
        TreeNode node = (TreeNode) expandableTreeAdapter.getItem(position);
        if (onExpandableListItemClickListener != null) {
            onExpandableListItemClickListener.onExpandableListItemClickListener(node.getContents());
        }
//        if (node.getChildrenNodeList() == null) {
//            if (onExpandableListItemClickListener != null) {
//                onExpandableListItemClickListener.onExpandableListItemClickListener(node.getContents());
//            }
//            return;
//        }
//        if (node.isExpand()) {
//            node.close();
//        } else {
//            node.expand();
//        }
//        expandableTreeAdapter.notifyDataSetChanged();
    }

    public void setOnExpandableListItemClickListener(OnExpandableListItemClickListener onExpandableListItemClickListener) {
        this.onExpandableListItemClickListener = onExpandableListItemClickListener;
    }

    interface OnExpandableListItemClickListener{
        void onExpandableListItemClickListener(TreeListNodeData treeNodeData);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof ExpandableTreeAdapter) {
            this.expandableTreeAdapter = (ExpandableTreeAdapter) adapter;
            expandableTreeAdapter.setOnIvRightClickListener(node -> {
                if (node.isExpand()) {
                    node.close();
                } else {
                    node.expand();
                }
                expandableTreeAdapter.notifyDataSetChanged();
            });
        }
    }
}
