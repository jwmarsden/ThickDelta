package com.ventia.gui.asset

import com.ventia.entities.LocationEntity
import com.ventia.entity.AssetEntity
import javax.swing.tree.DefaultMutableTreeNode

enum class AssetTreeNodeType {
    ROOT,
    LOCATION,
    ASSET,
    UNKNOWN
}


class AssetTreeViewNode(val type: AssetTreeNodeType, userObject: Any?, allowsChildren: Boolean): DefaultMutableTreeNode(userObject, allowsChildren) {

    override fun toString(): String {

        if(type == AssetTreeNodeType.LOCATION) {
            val locationObject = userObject as LocationEntity
            return "${locationObject.key} - ${locationObject.description}"
        } else if (type == AssetTreeNodeType.ASSET) {
            val assetObject = userObject as AssetEntity
            return "${assetObject.key} - ${assetObject.description}"
        } else if (type == AssetTreeNodeType.ROOT) {
            return userObject.toString()
        }


        return userObject.toString()
    }
}