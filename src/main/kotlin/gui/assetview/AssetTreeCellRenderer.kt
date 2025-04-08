package com.ventia.gui.assetview

import com.ventia.entities.LocationEntity
import com.ventia.entity.AssetEntity
import com.ventia.gui.asset.AssetTreeViewNode
import java.awt.Color
import java.awt.Component
import java.net.URL
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JTree
import javax.swing.SwingConstants
import javax.swing.tree.TreeCellRenderer

class AssetTreeCellRenderer : TreeCellRenderer {

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
        tree: JTree?,
        value: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ): Component {

        val assetTreeNode = value as AssetTreeViewNode
        if(assetTreeNode.type == AssetTreeNodeType.LOCATION) {
            val location = assetTreeNode.userObject as LocationEntity
            val label = if (location.maintainableFlag) {
                val l = JLabel(value.toString(), tagIcon, SwingConstants.CENTER)
                l.setForeground(Color.magenta)
                l
            } else {
                val l = JLabel(value.toString(), levelIcon, SwingConstants.CENTER)
                l.setForeground(Color.darkGray)
                l
            }
            return label
        } else if (assetTreeNode.type == AssetTreeNodeType.ASSET) {
            val asset = assetTreeNode.userObject as AssetEntity
            val label = if (asset.linear) {
                val l = JLabel(value.toString(), roadIcon, SwingConstants.CENTER)
                l.setForeground(Color.blue)
                l
            } else {
                val l = JLabel(value.toString(), assetIcon, SwingConstants.CENTER)
                l.setForeground(Color.blue)
                l
            }
            return label
        } else if (assetTreeNode.type == AssetTreeNodeType.ROOT) {
            val label = JLabel(
                value.toString(),
                rootIcon,
                SwingConstants.CENTER
            )
            return label
        }

        val label = JLabel(
            value.toString(),
            roadIcon,
            SwingConstants.CENTER
        )
        return label
    }

}