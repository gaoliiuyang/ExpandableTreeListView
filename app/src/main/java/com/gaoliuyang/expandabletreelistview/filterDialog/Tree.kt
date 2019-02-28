package com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog


/**
 * Created by gaoliuyang on 2018/12/12.
 */
class Tree(root: TreeListNodeData) {
    private var root: TreeNode? = null

    init {
        setRoot(root)
    }

    private fun setRoot(root: TreeListNodeData) {
        this.root = TreeNode(root)
        this.root!!.expand()
        root.level = 0
    }

    fun getRoot(): TreeNode? {
        return root
    }

    fun addChild(parent: TreeNode, child: TreeNode,index:Int) {
        parent.addChild(child,index)
    }

    /**
     * 深度优先搜索获得树的节点有序列表
     * @return 按照深度优先排序的节点列表
     */
    fun searchDepthFirst(): ArrayList<TreeNode> {
        val results = ArrayList<TreeNode>()
        results.add(root!!)
        searchDepthFirst(root!!, results)
        return results
    }

    /**
     * 获取指定id的节点
     * @param id 指定节点的id
     * @return 树节点
     */
    fun findNode(id: Int): TreeNode? {
        val holder = Holder()
        find(id, root!!, holder)
        return holder.node
    }

    private fun find(id: Int, node: TreeNode, holder: Holder) {
        if (node.getId() == id) {
            holder.node = node
            return
        }
        val children = node.getChildrenNodeList() ?: return
        for (i in 0 until children.size) {
            val child = children[i]
            find(id, child, holder)
            if (holder.node != null) {
                return
            }
        }
    }

    private fun searchDepthFirst(node: TreeNode, collection: ArrayList<TreeNode>) {
        if (!node.isExpand()) {
            return
        }
        val children = node.getChildrenNodeList() ?: return
        for (i in 0 until children.size) {
            val child = children[i]
            collection.add(child)
            searchDepthFirst(child, collection)
        }
    }

    private inner class Holder {
        internal var node: TreeNode? = null
    }
}