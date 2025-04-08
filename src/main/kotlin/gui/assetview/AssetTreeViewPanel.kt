package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.gui.asset.AssetTreeViewModel
import com.ventia.intent.Intent
import com.ventia.intent.IntentHub
import com.ventia.intent.IntentReceiver
import com.ventia.intent.PingIntent
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeSelectionModel


class AssetTreeViewPanel(private val controller: AssetController) : JPanel(), TreeSelectionListener, IntentReceiver {

    private lateinit var searchPanel: JPanel
    private lateinit var identifierQuery: JTextField
    private lateinit var descriptionQuery: JTextField

    private lateinit var tree: JTree

    override fun addNotify() {
        super.addNotify()


        searchPanel = JPanel()
        searchPanel.setLayout(BorderLayout())
        searchPanel.setBackground(Color.WHITE)

        val systemDropdownValues = arrayOf("Road", "Power", "Comms")
        val systemDropdown: JComboBox<*> = JComboBox<Any?>(systemDropdownValues)
        systemDropdown.setForeground(Color.GRAY)
        systemDropdown.setBackground(Color.WHITE)
        //systemDropdown.border = javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY,1)

        identifierQuery = JTextField("Key")
        identifierQuery.setColumns(8)
        identifierQuery.setToolTipText("Key Query")
        //identifierQuery.border = javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY,1)

        descriptionQuery = JTextField("Description")
        descriptionQuery.setToolTipText("Description Query")
        //descriptionQuery.border = javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY,1)

        identifierQuery.setForeground(Color.LIGHT_GRAY)
        identifierQuery.setBackground(Color.WHITE)
        descriptionQuery.setForeground(Color.LIGHT_GRAY)
        descriptionQuery.setBackground(Color.WHITE)

//        val lineStart = JPanel()
//        lineStart.border = javax.swing.BorderFactory.createEmptyBorder()
//        lineStart.setBackground(Color.WHITE)

        //lineStart.add(systemDropdown, BorderLayout.WEST)
        //lineStart.add(identifierQuery, BorderLayout.EAST);

        searchPanel.add(systemDropdown, BorderLayout.WEST)
        searchPanel.add(identifierQuery, BorderLayout.CENTER)
        searchPanel.add(descriptionQuery, BorderLayout.EAST)
        //searchPanel.add(busyLabel, BorderLayout.LINE_END);



        //background = Color.WHITE
        layout = BorderLayout()

        val top = DefaultMutableTreeNode("Road Hierarchy Root")

        val treeModel = AssetTreeViewModel(controller, top)
        treeModel.loadRoots()

        tree = JTree(treeModel)
        tree.isRootVisible = false
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        tree.border = EmptyBorder(4, 1, 4, 1)
        tree.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
        tree.addTreeSelectionListener(this);
        //tree!!.cellRenderer = DefaultTreeCellRenderer()
        tree.cellRenderer = AssetTreeCellRenderer()

        val treeView = JScrollPane(tree)

        val treeViewTreePanel = JPanel()
        treeViewTreePanel.layout = BorderLayout()
        treeViewTreePanel.add(searchPanel, BorderLayout.NORTH)
        treeViewTreePanel.add(treeView, BorderLayout.CENTER)

        layout = BorderLayout()

        add(treeViewTreePanel, BorderLayout.CENTER)

        IntentHub.lookup().registerForIntent(this, PingIntent::class.java)
    }

    override fun valueChanged(e: TreeSelectionEvent?) {
        val node = tree.getLastSelectedPathComponent() as DefaultMutableTreeNode

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