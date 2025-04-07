package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.entities.LocationEntity
import com.ventia.gui.asset.AssetTreeNodeType
import com.ventia.gui.asset.AssetTreeViewModel
import com.ventia.gui.asset.AssetTreeViewNode
import com.ventia.intent.Intent
import com.ventia.intent.IntentHub
import com.ventia.intent.IntentReceiver
import com.ventia.intent.PingIntent
import java.awt.*
import java.net.URL
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeCellRenderer
import javax.swing.tree.TreeSelectionModel


class AssetTreeViewPanel(private val controller: AssetController) : JPanel(), TreeSelectionListener, IntentReceiver {
    private var tree: JTree? = null

    override fun addNotify() {
        super.addNotify()
        background = Color.WHITE
        layout = BorderLayout()

        val top = AssetTreeViewNode(AssetTreeNodeType.ROOT,"Road Hierarchy Root", allowsChildren = true)

        val treeModel = AssetTreeViewModel(controller, top)
        treeModel.loadRoots()

        tree = JTree(treeModel)
        tree!!.isRootVisible = false
        tree!!.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        tree!!.border = EmptyBorder(4, 1, 4, 1)
        tree!!.font = Font(Font.MONOSPACED, Font.PLAIN, 14)
        tree!!.addTreeSelectionListener(this);
        //tree!!.cellRenderer = DefaultTreeCellRenderer()
        tree!!.cellRenderer = AssetTreeCellRenderer()

        val treeView = JScrollPane(tree)

        add(treeView, BorderLayout.CENTER)

        IntentHub.lookup().registerForIntent(this, PingIntent::class.java)
    }



    class AssetTreeCellRenderer : TreeCellRenderer {

        private val rootIconURL: URL? = this::class.java.getResource("/image/icon/icon-root/icon-root-18.png")
        private val rootIcon: ImageIcon = ImageIcon(rootIconURL);
        private val roadIconURL: URL? = this::class.java.getResource("/image/icon/icon-road/icon-road-18.png")
        private val roadIcon: ImageIcon = ImageIcon(roadIconURL);
        private val tagIconURL: URL? = this::class.java.getResource("/image/icon/icon-tag/icon-tag-18.png")
        private val tagIcon: ImageIcon = ImageIcon(tagIconURL);
        private val assetIconURL: URL? = this::class.java.getResource("/image/icon/icon-asset/icon-asset-18.png")
        private val assetIcon: ImageIcon = ImageIcon(assetIconURL);


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
                val label = JLabel(
                    value.toString(),
                    tagIcon,
                    SwingConstants.CENTER
                )
                return label
            } else if (assetTreeNode.type == AssetTreeNodeType.ASSET) {
                val label = JLabel(
                    value.toString(),
                    assetIcon,
                    SwingConstants.CENTER
                )
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


    override fun valueChanged(e: TreeSelectionEvent?) {
        val node = tree?.getLastSelectedPathComponent() as DefaultMutableTreeNode

        val nodeInfo = node.userObject
        if (node.isLeaf) {
            println(node)
            IntentHub.lookup().notifyIntent(PingIntent(controller))
        } else {
            println("Not Leaf")
        }
    }

    override fun receiveIntent(intent: Intent) {
        if (intent.javaClass == PingIntent::class.java) {
            println("Pong($intent)")
        }
    }

}