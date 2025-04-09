package com.ventia.gui.assetview

import com.ventia.entities.LocationEntity
import com.ventia.entity.AssetEntity
import com.ventia.gui.assetview.AssetTreeNodeType
import javax.swing.tree.DefaultMutableTreeNode



class AssetTreeViewNode(val type: AssetTreeNodeType, userObject: Any?, allowsChildren: Boolean): DefaultMutableTreeNode(userObject, allowsChildren) {


    override fun toString(): String {

        if(type == AssetTreeNodeType.LOCATION) {
            val locationObject = userObject as LocationEntity
            return "${locationObject.key} - ${locationObject.description}"
        } else if (type == AssetTreeNodeType.ASSET) {
            val assetObject = userObject as AssetEntity
            return "${assetObject.key} - ${assetObject.description}"
        } else if (type == AssetTreeNodeType.ROOT) {
            val locationObject = userObject as LocationEntity
            return "${locationObject.key} - ${locationObject.description}"
        }


        return userObject.toString()
    }
}
