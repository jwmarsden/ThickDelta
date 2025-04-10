package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.entities.SystemEntity
import com.ventia.gui.assetview.intent.SystemSelectedIntent
import com.ventia.intent.Intent
import com.ventia.intent.IntentHub
import com.ventia.intent.IntentReceiver
import com.ventia.intent.PingIntent
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeSelectionModel


class AssetTreeViewPanel(private val controller: AssetController) : JPanel(), TreeSelectionListener, IntentReceiver {

    private lateinit var toolPanel: JPanel
    private lateinit var searchPanel: JPanel
    private lateinit var identifierQuery: JTextField
    private lateinit var descriptionQuery: JTextField

    private lateinit var treeModel: AssetTreeViewModel
    private lateinit var tree: JTree

    override fun addNotify() {
        super.addNotify()


        toolPanel = JPanel()
        toolPanel.setLayout(BorderLayout())
        toolPanel.setBackground(Color.WHITE)

        searchPanel = JPanel()
        searchPanel.setLayout(BorderLayout())
        searchPanel.setBackground(Color.WHITE)

        val systemList = controller.getSystemList()
        val system = systemList.first()

        val systemDropdownValues = systemList.map { it }.toTypedArray()

        val systemDropdown: JComboBox<SystemEntity> = JComboBox<SystemEntity>(systemDropdownValues)
        systemDropdown.setForeground(Color.LIGHT_GRAY)
        systemDropdown.setBackground(Color.WHITE)

        systemDropdown.addActionListener(SystemActionListener())
        //systemDropdown.addItemListener(SystemItemListener())

        //systemDropdown.border = javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY,1)

        identifierQuery = JTextField("Id")
        identifierQuery.columns = 10
        identifierQuery.setToolTipText("Id Query")
        //identifierQuery.border = javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY,1)
        identifierQuery.isEnabled = false

        descriptionQuery = JTextField("Description")
        descriptionQuery.setToolTipText("Description Query")
        //descriptionQuery.border = javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY,1)
        descriptionQuery.isEnabled = false

        identifierQuery.setForeground(Color.LIGHT_GRAY)
        identifierQuery.setBackground(Color.WHITE)
        descriptionQuery.setForeground(Color.LIGHT_GRAY)
        descriptionQuery.setBackground(Color.WHITE)

        searchPanel.add(identifierQuery, BorderLayout.WEST)
        searchPanel.add(descriptionQuery, BorderLayout.CENTER)
        //searchPanel.add(busyLabel, BorderLayout.LINE_END);

        toolPanel.add(systemDropdown, BorderLayout.WEST)
        toolPanel.add(searchPanel, BorderLayout.CENTER)

        //background = Color.WHITE
        layout = BorderLayout()

        val top = DefaultMutableTreeNode("Road Hierarchy Root")
        treeModel = AssetTreeViewModel(controller, top, system)
        treeModel.loadRoots(system)

        tree = JTree(treeModel)
        tree.isRootVisible = false
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        tree.border = EmptyBorder(4, 1, 4, 1)
        tree.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
        tree.addTreeSelectionListener(this)
        //tree!!.cellRenderer = DefaultTreeCellRenderer()
        tree.cellRenderer = AssetTreeCellRenderer()

        val treeView = JScrollPane(tree)

        val treeViewTreePanel = JPanel()
        treeViewTreePanel.layout = BorderLayout()
        treeViewTreePanel.add(toolPanel, BorderLayout.NORTH)
        treeViewTreePanel.add(treeView, BorderLayout.CENTER)

        layout = BorderLayout()

        add(treeViewTreePanel, BorderLayout.CENTER)

        IntentHub.lookup().registerForIntent(this, PingIntent::class.java)
        IntentHub.lookup().registerForIntent(this, SystemSelectedIntent::class.java)
    }



    override fun valueChanged(e: TreeSelectionEvent?) {
        val selectedNode = tree.lastSelectedPathComponent
        if(selectedNode != null) {
            val node = selectedNode as DefaultMutableTreeNode
            if (node.isLeaf) {
                println(node)
                IntentHub.lookup().notifyIntent(PingIntent(controller))
            } else {
                println("Not Leaf")
            }
        }
    }

    override fun receiveIntent(intent: Intent) {
        if (intent.javaClass == SystemSelectedIntent::class.java) {
            val systemSelectedIntent = intent as SystemSelectedIntent
            if(systemSelectedIntent.system === treeModel.currentSystem) {
                println("Update to current System. Do nothing. ")
            } else {
                println("SystemSelectedIntent($intent)")
                val newSystem = systemSelectedIntent.system
                treeModel = AssetTreeViewModel(controller, DefaultMutableTreeNode(newSystem), newSystem)

                val top = DefaultMutableTreeNode("Road Hierarchy Root")
                treeModel = AssetTreeViewModel(controller, top, newSystem)
                tree.model = treeModel
                treeModel.loadRoots(newSystem)
                treeModel.reload()
            }
        }
        if (intent.javaClass == PingIntent::class.java) {
            println("Pong($intent)")
        }
    }


    private inner class SystemActionListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val source = e.source as JComboBox<*>
            IntentHub.lookup().notifyIntent(SystemSelectedIntent(controller, source.selectedItem as SystemEntity))
        }
    }
//    private inner class SystemItemListener : ItemListener {
//        override fun itemStateChanged(e: ItemEvent?) {
//            println("Event: $e")
//        }
//    }


}