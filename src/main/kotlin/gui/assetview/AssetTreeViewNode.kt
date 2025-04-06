package com.ventia.gui.asset

import com.ventia.entities.LocationEntity
import javax.swing.tree.DefaultMutableTreeNode

class AssetTreeViewNode(userObject: Any?, allowsChildren: Boolean): DefaultMutableTreeNode(userObject, allowsChildren) {

    override fun toString(): String {
        if(userObject is LocationEntity) {
            val locationObject = userObject as LocationEntity
            return "${locationObject.key} - ${locationObject.description}"
        }
        return userObject.toString()
    }
}