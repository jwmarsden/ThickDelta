package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import java.awt.*
import java.net.URL
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSplitPane


class AssetHierarchyView(private val controller: AssetController): JPanel() {

    private var splitPane: JSplitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT)

    override fun addNotify() {
        super.addNotify()

        layout = BorderLayout()

        splitPane.setDividerLocation(540)
        splitPane.setOneTouchExpandable(true)
        splitPane.setContinuousLayout(true)

        val splitPanel = JPanel()
        splitPanel.layout = BorderLayout()
        splitPanel.add(AssetTreeViewPanel(controller), BorderLayout.CENTER)

        splitPanel.background = Color.decode("#1D4A5C")
        var logo: Image? = null
        try {
            val logoUrl: URL? = this::class.java.getResource("/image/T2D_Logo.png")
            val kit: Toolkit = Toolkit.getDefaultToolkit()
            logo = kit.getImage(logoUrl)
        } catch (_: Exception) { }
        if (logo != null) {
            val imageLabel = JLabel(ImageIcon(logo))
            //imageLabel.setBorder(NiceBorder(Color.GRAY, Insets(4, 5, 4, 5), false, false, true, true));
            splitPanel.add(imageLabel, BorderLayout.SOUTH)
        }


        splitPane.leftComponent = splitPanel

        val assetDetailView: AssetDetailView = AssetDetailView(controller)
        splitPane.rightComponent = assetDetailView

        add(splitPane, BorderLayout.CENTER)
    }
}