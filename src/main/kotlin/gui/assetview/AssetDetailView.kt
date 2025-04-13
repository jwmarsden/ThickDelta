package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.gui.assetview.intent.AssetSelectedIntent
import com.ventia.gui.assetview.intent.LocationSelectedIntent
import com.ventia.gui.assetview.intent.RootSelectedIntent
import com.ventia.gui.assetview.intent.SystemSelectedIntent
import com.ventia.intent.Intent
import com.ventia.intent.IntentHub
import com.ventia.intent.IntentReceiver
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JSplitPane
import javax.swing.JTabbedPane

class AssetDetailView(private val controller: AssetController): JPanel(), IntentReceiver {

    init {
        IntentHub.lookup().registerForIntent(this, SystemSelectedIntent::class.java)
        IntentHub.lookup().registerForIntent(this, LocationSelectedIntent::class.java)
        IntentHub.lookup().registerForIntent(this, AssetSelectedIntent::class.java)
        IntentHub.lookup().registerForIntent(this, RootSelectedIntent::class.java)
    }

    override fun addNotify() {
        super.addNotify()
        layout = BorderLayout()

        val tabbedPane = JTabbedPane(JTabbedPane.BOTTOM)
        tabbedPane.border = BorderFactory.createEtchedBorder()

        tabbedPane.addTab("Details", JPanel())
        tabbedPane.addTab("Documents", JPanel())

        add(tabbedPane, BorderLayout.CENTER)
    }

    override fun receiveIntent(intent: Intent) {
        println("AssetDetailView received intent of $intent")
    }
}