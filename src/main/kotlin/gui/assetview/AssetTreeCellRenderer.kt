package com.ventia.gui.assetview

import com.ventia.entities.LocationEntity
import com.ventia.entity.AssetEntity
import com.ventia.gui.asset.AssetTreeViewNode
import org.hsqldb.result.ResultMetaData.SysOffsets
import java.awt.Color
import java.awt.Component
import java.net.URL
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JTree
import javax.swing.SwingConstants
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.TreeCellRenderer


class AssetTreeCellRenderer : DefaultTreeCellRenderer() {

    private val rootIconURL: URL? = this::class.java.getResource("/image/icon/icon-root/icon-root-18.png")
    private val rootIcon: ImageIcon = ImageIcon(rootIconURL);
    private val roadIconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road-18.png")
    private val roadIcon: ImageIcon = ImageIcon(roadIconURL);
    private val tagIconURL: URL? = this::class.java.getResource("/image/icon/icon-tag/icon-tag-18.png")
    private val tagIcon: ImageIcon = ImageIcon(tagIconURL);
    private val assetIconURL: URL? = this::class.java.getResource("/image/icon/icon-asset/icon-asset-18.png")
    private val assetIcon: ImageIcon = ImageIcon(assetIconURL);
    private val levelIconURL: URL? = this::class.java.getResource("/image/icon/icon-level/icon-level-18.png")
    private val levelIcon: ImageIcon = ImageIcon(levelIconURL);


    override fun getTreeCellRendererComponent(
        tree: JTree,
        value: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ): Component {
        val fieldLabel = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus) as JLabel

        val assetTreeNode = value as AssetTreeViewNode
        if(assetTreeNode.type == AssetTreeNodeType.LOCATION) {
            val location = assetTreeNode.userObject as LocationEntity
            if (location.maintainableFlag) {
                fieldLabel.foreground = Color.magenta
                fieldLabel.icon = tagIcon
            } else {
                fieldLabel.foreground = Color.darkGray
                fieldLabel.icon = levelIcon
            }
        } else if (assetTreeNode.type == AssetTreeNodeType.ASSET) {
            val asset = assetTreeNode.userObject as AssetEntity
            if (asset.linear) {
                fieldLabel.foreground = Color.blue
                fieldLabel.icon = roadIcon
            } else {
                fieldLabel.foreground = Color.blue
                fieldLabel.icon = assetIcon
            }
        } else {
            fieldLabel.icon = rootIcon
        }

        if(selected) {
            fieldLabel.foreground = Color.white
            fieldLabel.background = Color.green
        }

        return fieldLabel
    }

}