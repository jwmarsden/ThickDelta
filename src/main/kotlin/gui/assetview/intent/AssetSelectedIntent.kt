package com.ventia.gui.assetview.intent

import com.ventia.controller.Controller
import com.ventia.entity.AssetEntity
import com.ventia.intent.Intent
import javax.swing.tree.DefaultMutableTreeNode

class AssetSelectedIntent(source: Controller, val asset: AssetEntity) : Intent(source) {
    override fun toString(): String {
        return "AssetSelectedIntent(asset=$asset)"
    }
}