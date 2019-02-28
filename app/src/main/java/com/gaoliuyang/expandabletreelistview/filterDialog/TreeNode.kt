package com.onetarget.yujing3.teacher.modules.report.personalReport.filterDialog


/**
 * Created by gaoliuyang on 2018/12/12.
 */
class TreeNode(contents: TreeListNodeData) {

    private var contents: TreeListNodeData? = null
    private var parentNode: TreeNode? = null
    private var childrenNodeList: ArrayList<TreeNode>? = null
    private var expand = false

    init {
        setContents(contents)
    }

    fun getContents(): TreeListNodeData? {
        return contents
    }

    fun getParentNode(): TreeNode? {
        return parentNode
    }

    fun getChildrenNodeList(): ArrayList<TreeNode>? {
        return childrenNodeList
    }

    private fun setContents(contents: TreeListNodeData) {
        this.contents = contents
    }

    private fun setParentNode(parentNode: TreeNode) {
        this.parentNode = parentNode
    }

    fun expand() {
        this.expand = true
    }

    fun close() {
        this.expand = false
    }

    fun isExpand(): Boolean {
        return expand
    }

    fun getId(): Int {
        return contents!!.id
    }

    fun getParentId(): Int {
        return contents!!.parentId
    }

    fun addChild(child: TreeNode,index:Int) {
        if (childrenNodeList == null) {
            childrenNodeList = ArrayList()
        }
        childrenNodeList!!.add(child)
        child.setParentNode(this)
        child.setLevel(getLevel() + 1)
        child.expand = index == 1 || index == 2
    }

    private fun getLevel(): Int {
        return contents!!.level
    }

    private fun setLevel(level: Int) {
        contents!!.level = level
    }
}