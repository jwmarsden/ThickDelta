package com.ventia.gui.assetview.intent

import com.ventia.controller.Controller
import com.ventia.intent.Intent
import javax.swing.tree.DefaultMutableTreeNode

class AssetTreeNodeSelectedIntent(source: Controller, val node: Any?) : Intent(source) {
}