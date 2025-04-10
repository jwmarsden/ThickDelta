package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.entities.LocationEntity
import com.ventia.entities.SystemEntity
import com.ventia.entity.AssetEntity
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeNode

class AssetTreeViewModel(private val controller: AssetController, root: TreeNode?, var currentSystem:SystemEntity) : DefaultTreeModel(root) {
    private var roots: MutableList<LocationEntity> = mutableListOf()
    private var locationTreeNodes: MutableMap<LocationEntity, AssetTreeViewNode> = mutableMapOf()
    private var assetTreeNodes: MutableMap<AssetEntity, AssetTreeViewNode> = mutableMapOf()
    private var notFoundNode: AssetTreeViewNode = AssetTreeViewNode(AssetTreeNodeType.UNKNOWN,"Not found", true)

    override fun getRoot(): Any? {
        return root
    }

    override fun getChildCount(parent: Any?): Int {
        return getChildCount(parent as TreeNode)
    }

    override fun getChild(parent: Any?, index: Int): AssetTreeViewNode {
        return getChild(parent as TreeNode, index)
    }

    private fun getChildCount(parent: TreeNode): Int {
        val size = if(parent is AssetTreeViewNode) {
            if (parent.userObject is LocationEntity) {
                val parentLocation: LocationEntity = parent.userObject as LocationEntity
                val assets = parentLocation.getAssetsWithoutParent()
                val children = controller.getChildrenInSystem(parentLocation, currentSystem)
                children.size + assets.size
            } else if (parent.userObject is AssetEntity) {
                val parentAsset: AssetEntity = parent.userObject as AssetEntity
                parentAsset.children.size
            } else {
                0
            }
        } else if(parent is DefaultMutableTreeNode) {
            return roots.size
        } else {
            0
        }
        return size
    }

    private fun getChild(parent: TreeNode, index: Int): AssetTreeViewNode {
        if (parent is AssetTreeViewNode) {
            if(parent.type == AssetTreeNodeType.LOCATION || parent.type == AssetTreeNodeType.ROOT) {
                val parentLocation: LocationEntity = parent.userObject as LocationEntity
                val assetChildren = parentLocation.getAssetsWithoutParent()
                val locationChildren = controller.getChildrenInSystem(parentLocation, currentSystem)
                val assetChildrenSize = assetChildren.size
                val locationChildrenSize = locationChildren.size
                val node: AssetTreeViewNode = if (assetChildren.isEmpty()) {
                    val location = locationChildren[index]
                    getTreeNode(location)
                } else if (index < assetChildrenSize) {
                    val asset = assetChildren[index]
                    getTreeNode(asset)
                } else if (index < assetChildrenSize+locationChildrenSize) {
                    val location = locationChildren[index-assetChildrenSize]
                    getTreeNode(location)
                } else {
                    notFoundNode
                }
                return node
            } else if (parent.type == AssetTreeNodeType.ASSET) {
                val parentAsset: AssetEntity = parent.userObject as AssetEntity
                val assets = parentAsset.children
                val asset = assets[index]
                return getTreeNode(asset)
            }
        } else if (parent is DefaultMutableTreeNode) {
            val rootLocation = roots[index]
            return getTreeNode(rootLocation)
        }
        return notFoundNode
    }

    override fun isLeaf(node: Any?): Boolean {
        if (node is AssetTreeViewNode) {
            val isLeaf = if(node.type == AssetTreeNodeType.LOCATION) {
                val nodeLocation: LocationEntity = node.userObject as LocationEntity
                nodeLocation.children.isEmpty() && nodeLocation.assets.isEmpty()
            } else if (node.type == AssetTreeNodeType.ASSET) {
                val nodeAsset: AssetEntity = node.userObject as AssetEntity
                nodeAsset.children.isEmpty()
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

    fun loadRoots(system: SystemEntity) {
        val locationRoots: List<LocationEntity> = controller.lookupLocationRoots(system)
        for (root in locationRoots) {
            roots.add(root)
            locationTreeNodes[root] = AssetTreeViewNode(AssetTreeNodeType.ROOT, root, true)
        }
    }

    private fun getTreeNode(entity: Any): AssetTreeViewNode {
        val returnNode = if(entity is LocationEntity) {
            val locationEntity = entity
            val returnNode = if (!locationTreeNodes.containsKey(locationEntity)) {
                val newNode = AssetTreeViewNode(AssetTreeNodeType.LOCATION, locationEntity, true)
                locationTreeNodes[locationEntity] = newNode
                newNode
            } else {
                locationTreeNodes[locationEntity]
            }
            returnNode
        } else if(entity is AssetEntity) {
            val assetEntity = entity
            val returnNode = if (!assetTreeNodes.containsKey(assetEntity)) {
                val newNode = AssetTreeViewNode(AssetTreeNodeType.ASSET, assetEntity, true)
                assetTreeNodes[assetEntity] = newNode
                newNode
            } else {
                assetTreeNodes[assetEntity]
            }
            returnNode
        } else {
            notFoundNode
        }
        return returnNode!!
    }
}