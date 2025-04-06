package com.ventia.gui.asset

import com.ventia.controller.AssetController
import com.ventia.entities.LocationEntity
import java.net.URL
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.event.TreeModelListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeModel
import javax.swing.tree.TreeNode
import javax.swing.tree.TreePath


class AssetTreeViewModel(val controller: AssetController, val root: TreeNode?) : TreeModel {
    var roots: MutableList<LocationEntity>? = null
    var treeNodes: MutableMap<LocationEntity, AssetTreeViewNode>? = null

    override fun getRoot(): Any? {
        return root
    }

    override fun getChildCount(parent: Any?): Int {
        val genericNode: TreeNode = parent as TreeNode;

        if (parent is AssetTreeViewNode) {
            val parentLocation: LocationEntity = (parent as AssetTreeViewNode).userObject as LocationEntity
            return parentLocation.children.size
        }
        if (parent is DefaultMutableTreeNode) {
            return roots!!.size
        }

        return 0
    }

    override fun getChild(parent: Any?, index: Int): AssetTreeViewNode? {
        if (parent is AssetTreeViewNode) {
            val parentLocation: LocationEntity = (parent as AssetTreeViewNode).userObject as LocationEntity

            val node = AssetTreeViewNode(parentLocation.children[index], true)

            return node
        }
        if (parent is DefaultMutableTreeNode) {
            return AssetTreeViewNode(roots?.get(index), true)
        }
        return AssetTreeViewNode("Not found", true)
    }

    override fun isLeaf(node: Any?): Boolean {
        if (node is AssetTreeViewNode) {
            val nodeLocation: LocationEntity = (node as AssetTreeViewNode).userObject as LocationEntity
            return nodeLocation.children.isEmpty()
        }
        if (node is DefaultMutableTreeNode) {
            return false
        }
        return true
    }

    override fun valueForPathChanged(path: TreePath?, newValue: Any?) {

    }

    override fun getIndexOfChild(parent: Any?, child: Any?): Int {
        return 0
    }

    override fun addTreeModelListener(l: TreeModelListener?) {

    }

    override fun removeTreeModelListener(l: TreeModelListener?) {

    }

    fun loadRoots() {
        if (roots != null) {
            roots?.clear()
        } else {
            roots = mutableListOf()
        }
        if (treeNodes != null) {
            treeNodes?.clear()
        } else {
            treeNodes = mutableMapOf()
        }
        val locationRoots = controller.lookupLocationRoots();
        for (root in locationRoots!!) {
            roots!!.add(root)
            treeNodes!![root] = AssetTreeViewNode(root, true)
        }
        print("Roots: $locationRoots")
    }
}