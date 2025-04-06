package com.ventia.gui.asset

import com.ventia.controller.AssetController
import com.ventia.intent.Intent
import com.ventia.intent.IntentHub
import com.ventia.intent.IntentReceiver
import com.ventia.intent.PingIntent
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.border.EmptyBorder
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeSelectionModel


class AssetTreeViewPanel(private val controller: AssetController) : JPanel(), TreeSelectionListener, IntentReceiver {
    var tree: JTree? = null

    override fun addNotify() {
        super.addNotify()
        background = Color.WHITE
        layout = BorderLayout()

        val top = DefaultMutableTreeNode("Road Hierarchy Root")

        val treeModel = AssetTreeViewModel(controller, top)
        treeModel.loadRoots()

        tree = JTree(treeModel)
        tree!!.isRootVisible = false
        tree!!.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        tree!!.border = EmptyBorder(4, 0, 4, 0)
        tree!!.font = Font(Font.MONOSPACED, Font.PLAIN, 10)
        tree!!.addTreeSelectionListener(this);



        val treeView = JScrollPane(tree)


        add(treeView, BorderLayout.CENTER)

        IntentHub.lookup().registerForIntent(this, PingIntent::class.java)
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