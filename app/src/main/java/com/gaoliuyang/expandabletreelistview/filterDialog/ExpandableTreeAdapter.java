package com.gaoliuyang.expandabletreelistview.filterDialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gaoliuyang.expandabletreelistview.R;
import com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog.Tree;
import com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog.TreeListNodeData;
import com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog.TreeNode;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 服务ExpandableTreeListView的数据源Adapter
 */
public abstract class ExpandableTreeAdapter extends BaseAdapter {

    private Tree listDataTree;
    private ArrayList<TreeNode> treeNodeArrayList;
    private Context context;
    private OnIvRightClickListener onRightClickListener;

    /**
     * 实例化一个ExpandableTreeAdapter，其数据源为基于listData生成的树
     *
     * @param listData 数据列表
     */
    public ExpandableTreeAdapter(ArrayList<TreeListNodeData> listData, Context context) {
        this.context = context;
        listDataTree = generateTree(listData);
    }

    private Tree generateTree(ArrayList<TreeListNodeData> listData) {
        Tree tree = new Tree(listData.get(0));

        for (int i = 1; i < listData.size(); i++) {
            TreeNode child = new TreeNode(listData.get(i));
            TreeNode parent = tree.findNode(child.getParentId());
            assert parent != null;
            tree.addChild(parent, child,i);
        }

        treeNodeArrayList = tree.searchDepthFirst();
        if (!showRoot()) {
            treeNodeArrayList.remove(0);
        }

        return tree;
    }

    @Override
    public Object getItem(int position) {
        return treeNodeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return treeNodeArrayList.get(position).getId();
    }

    @Override
    public int getCount() {
        return treeNodeArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TreeNode node = treeNodeArrayList.get(position);
        TreeListNodeData nodeData = node.getContents();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filter_evaluation_result, null);
            convertView.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 180));
        }
        RelativeLayout rlItem = convertView.findViewById(R.id.rl_item);
        View leftView = convertView.findViewById(R.id.view_left);
        TextView tvContent = convertView.findViewById(R.id.tv_content);
        ImageView ivRight = convertView.findViewById(R.id.iv_array);
        View dividerBottom = convertView.findViewById(R.id.divider_bottom);
        View dividerTop = convertView.findViewById(R.id.divider_top);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (nodeData.getLevel() == 3) {
            leftView.setVisibility(View.GONE);
            ivRight.setVisibility(View.GONE);
            dividerTop.setVisibility(View.GONE);
            dividerBottom.setVisibility(View.GONE);
            rlItem.setBackgroundColor(ContextCompat.getColor(context, R.color.YT_f2f2f5));
            params.setMargins(148, 0, 0, 0);
            tvContent.setTextColor(ContextCompat.getColor(context, R.color.textColorSecond));
        } else {
            if (nodeData.getLevel() == 2) {
                leftView.setVisibility(View.GONE);
                dividerTop.setVisibility(View.GONE);
                params.setMargins(88, 0, 0, 0);
                rlItem.setBackgroundColor(ContextCompat.getColor(context, R.color.YT_f8f8f8));
            } else {
                leftView.setVisibility(View.VISIBLE);
                dividerTop.setVisibility(View.VISIBLE);
                params.setMargins(28, 0, 0, 0);
                rlItem.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
            if (position == treeNodeArrayList.size() - 1) {
                dividerBottom.setVisibility(View.VISIBLE);
            } else {
                dividerBottom.setVisibility(View.GONE);
            }
            if (((TreeNode) getItem(position)).getChildrenNodeList() == null) {
                ivRight.setVisibility(View.GONE);
            } else {
                ivRight.setVisibility(View.VISIBLE);
            }
            tvContent.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary));
        }
        tvContent.setLayoutParams(params);
        tvContent.setText(Objects.requireNonNull(nodeData.getPersonalReportData()).getTitle());
        if (((TreeNode) getItem(position)).isExpand()) {
            ivRight.setImageResource(R.mipmap.icon_array_up);
        } else {
            ivRight.setImageResource(R.mipmap.icon_array_down);
        }
        ivRight.setOnClickListener(v -> {
            if (onRightClickListener != null) {
                onRightClickListener.rightClickListener(node);
            }
        });
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        treeNodeArrayList = listDataTree.searchDepthFirst();
        if (!showRoot()) {
            treeNodeArrayList.remove(0);
        }
        super.notifyDataSetChanged();
    }

    void setOnIvRightClickListener(OnIvRightClickListener onIvRightClickListener) {
        this.onRightClickListener = onIvRightClickListener;
    }

    interface OnIvRightClickListener {
        void rightClickListener(TreeNode node);
    }

    /**
     * 是否显示根节点
     *
     * @return 是否显示根节点
     */
    protected abstract boolean showRoot();
}
