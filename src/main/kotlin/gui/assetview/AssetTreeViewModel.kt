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
        //val genericNode: TreeNode = parent as TreeNode;

        if (parent is AssetTreeViewNode) {
            if(parent.type == AssetTreeNodeType.LOCATION) {
                val nodeLocation: LocationEntity = parent.userObject as LocationEntity
                return nodeLocation.children.size + nodeLocation.assets.size
            } else if (parent.type == AssetTreeNodeType.ASSET) {
                return 0
            } else if (parent.type == AssetTreeNodeType.ROOT) {
                return roots!!.size
            }

        }


        return 0
    }

    override fun getChild(parent: Any?, index: Int): AssetTreeViewNode? {
        if (parent is AssetTreeViewNode) {
            if(parent.type == AssetTreeNodeType.LOCATION) {
                val parentLocation: LocationEntity = (parent as AssetTreeViewNode).userObject as LocationEntity

                val assetsSize = parentLocation.assets.size
                val childrenSize = parentLocation.children.size


                val node: AssetTreeViewNode = if(index < assetsSize) {
                    AssetTreeViewNode(AssetTreeNodeType.ASSET, parentLocation.assets[index], true)
                } else if (index < childrenSize+assetsSize) {
                    AssetTreeViewNode(AssetTreeNodeType.LOCATION, parentLocation.children[index-assetsSize], true)
                } else {
                    AssetTreeViewNode(AssetTreeNodeType.UNKNOWN,"Not found", true)
                }

                return node
            } else if (parent.type == AssetTreeNodeType.ASSET) {
                return AssetTreeViewNode(AssetTreeNodeType.UNKNOWN,"Not found", true)
            } else if (parent.type == AssetTreeNodeType.ROOT) {
                return AssetTreeViewNode(AssetTreeNodeType.LOCATION, roots?.get(index), true)
            }
        }

        return AssetTreeViewNode(AssetTreeNodeType.UNKNOWN,"Not found", true)
    }

    override fun isLeaf(node: Any?): Boolean {
        if (node is AssetTreeViewNode) {
            val isLeaf = if(node.type == AssetTreeNodeType.LOCATION) {
                val nodeLocation: LocationEntity = node.userObject as LocationEntity
                nodeLocation.children.isEmpty() && nodeLocation.assets.isEmpty()
            } else if (node.type == AssetTreeNodeType.ASSET) {
                true
            } else if (node.type == AssetTreeNodeType.ROOT) {
                false
            } else {
                true
            }
            return isLeaf
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
        val locationRoots: MutableList<LocationEntity> = controller.lookupLocationRoots();
        for (root in locationRoots) {
            roots!!.add(root)
            treeNodes!![root] = AssetTreeViewNode(AssetTreeNodeType.LOCATION, root, true)
        }
        print("Roots: $locationRoots")
    }
}