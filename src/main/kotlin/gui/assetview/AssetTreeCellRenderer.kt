package com.ventia.gui.assetview

import com.ventia.entities.LocationEntity
import com.ventia.entity.AssetEntity
import java.awt.Color
import java.awt.Component
import java.net.URL
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JTree
import javax.swing.tree.DefaultTreeCellRenderer


class AssetTreeCellRenderer : DefaultTreeCellRenderer() {

    private val rootIconURL: URL? = this::class.java.getResource("/image/icon/icon-root/icon-root-18.png")
    private val rootIcon: ImageIcon = ImageIcon(rootIconURL);
    private val roadIconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road-2-18.png")
    private val roadIcon: ImageIcon = ImageIcon(roadIconURL);

    private val roadLIconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-l-18.png")
    private val roadLIcon: ImageIcon = ImageIcon(roadLIconURL);

    private val roadL1IconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-l1-18.png")
    private val roadL1Icon: ImageIcon = ImageIcon(roadL1IconURL);
    private val roadL2IconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-l2-18.png")
    private val roadL2Icon: ImageIcon = ImageIcon(roadL2IconURL);
    private val roadL3IconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-l3-18.png")
    private val roadL3Icon: ImageIcon = ImageIcon(roadL3IconURL);

    private val roadLxIconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-lx-18.png")
    private val roadLxIcon: ImageIcon = ImageIcon(roadLxIconURL);

    private val roadRIconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-r-18.png")
    private val roadRIcon: ImageIcon = ImageIcon(roadRIconURL);

    private val roadR1IconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-r1-18.png")
    private val roadR1Icon: ImageIcon = ImageIcon(roadR1IconURL);
    private val roadR2IconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-r2-18.png")
    private val roadR2Icon: ImageIcon = ImageIcon(roadR2IconURL);
    private val roadR3IconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-r3-18.png")
    private val roadR3Icon: ImageIcon = ImageIcon(roadR3IconURL);

    private val roadRxIconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road2-rx-18.png")
    private val roadRxIcon: ImageIcon = ImageIcon(roadRxIconURL);

    private val tagIconURL: URL? = this::class.java.getResource("/image/icon/icon-tag/icon-tag-18.png")
    private val tagIcon: ImageIcon = ImageIcon(tagIconURL);
    private val assetIconURL: URL? = this::class.java.getResource("/image/icon/icon-asset/icon-asset-18.png")
    private val assetIcon: ImageIcon = ImageIcon(assetIconURL);
    private val levelIconURL: URL? = this::class.java.getResource("/image/icon/icon-level/icon-level-18.png")
    private val levelIcon: ImageIcon = ImageIcon(levelIconURL);
    private val level1IconURL: URL? = this::class.java.getResource("/image/icon/icon-level/icon-level-1-18.png")
    private val level1Icon: ImageIcon = ImageIcon(level1IconURL);
    private val level2IconURL: URL? = this::class.java.getResource("/image/icon/icon-level/icon-level-2-18.png")
    private val level2Icon: ImageIcon = ImageIcon(level2IconURL);
    private val level3IconURL: URL? = this::class.java.getResource("/image/icon/icon-level/icon-level-3-18.png")
    private val level3Icon: ImageIcon = ImageIcon(level3IconURL);
    private val facilityIconURL: URL? = this::class.java.getResource("/image/icon/icon-facility/icon-facility-18.png")
    private val facilityIcon: ImageIcon = ImageIcon(facilityIconURL);
    private val councilIconURL: URL? = this::class.java.getResource("/image/icon/icon-council/icon-council-18.png")
    private val councilIcon: ImageIcon = ImageIcon(councilIconURL);

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
        if (value is AssetTreeViewNode) {
            val assetTreeNode = value as AssetTreeViewNode
            if(assetTreeNode.type == AssetTreeNodeType.LOCATION) {
                val location = assetTreeNode.userObject as LocationEntity
                if (location.maintainableFlag) {
                    fieldLabel.foreground = Color(245, 130, 33)

                    fieldLabel.icon = tagIcon
                } else {

                    fieldLabel.icon = if(location.type?.type == "GROUP") {
                        if(location.key == "ROADS") {
                            roadIcon
                        } else if (location.key == "FACILITIES") {
                            facilityIcon
                        } else {
                            councilIcon
                        }
                    } else if(location.level == 2) {
                        level1Icon
                    } else if(location.level == 3) {
                        level2Icon
                    } else if(location.level == 4) {
                        level3Icon
                    } else {
                        levelIcon
                    }

                    fieldLabel.foreground = Color(29, 74, 92)
                }
            } else if (assetTreeNode.type == AssetTreeNodeType.ASSET) {
                val asset = assetTreeNode.userObject as AssetEntity
                if (asset.linear) {
                    if(asset.type?.type == "CARRIAGEWAY") {
                        fieldLabel.foreground = Color.red
                        if(asset.key.endsWith("L")) {
                            fieldLabel.icon = roadLIcon
                        } else {
                            fieldLabel.icon = roadRIcon
                        }

                    } else if(asset.type?.type == "LANE") {
                        fieldLabel.foreground = Color.red
                        if (asset.key.endsWith("-L1")) {
                            fieldLabel.icon = roadL1Icon
                        } else if (asset.key.endsWith("-L2")) {
                            fieldLabel.icon = roadL2Icon
                        } else if (asset.key.endsWith("-L3")) {
                            fieldLabel.icon = roadL3Icon
                        } else if (asset.key.endsWith("-R1")) {
                            fieldLabel.icon = roadR1Icon
                        } else if (asset.key.endsWith("-R2")) {
                            fieldLabel.icon = roadR2Icon
                        } else if (asset.key.endsWith("-R3")) {
                            fieldLabel.icon = roadR3Icon
                        } else if (asset.key.contains("-R")) {
                            fieldLabel.icon = roadRxIcon
                        } else {
                            fieldLabel.icon = roadLxIcon
                        }
                    } else {
                        fieldLabel.foreground = Color.blue
                        fieldLabel.icon = roadIcon
                    }

                } else {
                    fieldLabel.foreground = Color(49, 211, 151)
                    fieldLabel.icon = assetIcon
                }
            } else {
                fieldLabel.icon = rootIcon
            }

            if(selected) {
                fieldLabel.foreground = Color.white
                fieldLabel.background = Color.green
            }
        }
        return fieldLabel
    }

}