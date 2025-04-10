package com.ventia.gui.assetview

import com.ventia.entities.LocationEntity
import com.ventia.entity.AssetEntity
import com.ventia.gui.assetview.AssetTreeNodeType
import javax.swing.tree.DefaultMutableTreeNode



class AssetTreeViewNode(val type: AssetTreeNodeType, userObject: Any?, allowsChildren: Boolean): DefaultMutableTreeNode(userObject, allowsChildren) {

    override fun toString(): String {
        if(type == AssetTreeNodeType.LOCATION) {
            val locationObject = userObject as LocationEntity
            return if(locationObject.type != null && !locationObject.type.type.equals("") && !locationObject.type.type.equals("UNK")) {
                "${locationObject.key} - (${locationObject.type}) ${locationObject.description}"
            } else {
                "${locationObject.key} - ${locationObject.description}"
            }
        } else if (type == AssetTreeNodeType.ASSET) {
            val assetObject = userObject as AssetEntity
            return "${assetObject.key} - (${assetObject.type}) ${assetObject.description}"
        } else if (type == AssetTreeNodeType.ROOT) {
            val locationObject = userObject as LocationEntity
            return "${locationObject.key} - ${locationObject.description}"
        }
        return userObject.toString()
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass)
            return false
        if (other is AssetTreeViewNode) {
            val otherUserObject = other.userObject
            if(other.type == AssetTreeNodeType.LOCATION || other.type == AssetTreeNodeType.ROOT) {
                val otherLocationObject = otherUserObject as LocationEntity
                if(type == AssetTreeNodeType.LOCATION || type == AssetTreeNodeType.ROOT) {
                    val locationObject = userObject as LocationEntity
                    return locationObject == otherLocationObject
                } else if (type == AssetTreeNodeType.ASSET) {
                    return false
                }
            } else if (other.type == AssetTreeNodeType.ASSET) {
                val otherAssetObject = otherUserObject as AssetEntity
                if(type == AssetTreeNodeType.LOCATION || type == AssetTreeNodeType.ROOT) {
                    return false
                } else if (type == AssetTreeNodeType.ASSET) {
                    val assetEntity = userObject as AssetEntity
                    return assetEntity == otherAssetObject
                }
            }
        }
        return false
    }

    override fun hashCode(): Int {
        if(type == AssetTreeNodeType.LOCATION || type == AssetTreeNodeType.ROOT) {
            val locationObject = userObject as LocationEntity
            return locationObject.hashCode()
        } else if (type == AssetTreeNodeType.ASSET) {
            val assetObject = userObject as AssetEntity
            return assetObject.hashCode()
        } else {
            return 0
        }
    }

}
