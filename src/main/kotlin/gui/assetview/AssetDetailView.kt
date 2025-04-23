package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.entity.classification.ClassificationComplexEntityLocationEntity
import com.ventia.entity.classification.ClassificationEntity
import com.ventia.gui.assetview.intent.AssetSelectedIntent
import com.ventia.gui.assetview.intent.LocationSelectedIntent
import com.ventia.gui.assetview.intent.RootSelectedIntent
import com.ventia.gui.assetview.intent.SystemSelectedIntent
import com.ventia.intent.Intent
import com.ventia.intent.IntentHub
import com.ventia.intent.IntentReceiver
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

class AssetDetailView(private val controller: AssetController): JPanel(), IntentReceiver {

    val detailsTab: DetailsTab = DetailsTab()

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

        tabbedPane.addTab("Details", detailsTab)
        //tabbedPane.addTab("Documents", JPanel())

        add(tabbedPane, BorderLayout.CENTER)
    }

    override fun receiveIntent(intent: Intent) {
        println("AssetDetailView received intent of $intent")

        if(intent is LocationSelectedIntent) {
            if(intent.location.classification != null) {
                val classification: ClassificationEntity = intent.location.classification
                println(classification.getPathString())
                detailsTab.classificationDescription.text = classification.toString()

                if(classification is ClassificationComplexEntityLocationEntity) {
                    //val classificationComplextEntityLocation = classification as ClassificationComplexEntityLocationEntity
                    detailsTab.complexField.text = classification.complex?.code
                    detailsTab.complexFieldDescription.text = classification.complex?.title

                    detailsTab.entityField.text = classification.entity?.code
                    detailsTab.entityFieldDescription.text = classification.entity?.title

                    detailsTab.spaceField.text = classification.spaceLocation?.code
                    detailsTab.spaceFieldDescription.text = classification.spaceLocation?.title

                }





                detailsTab.repaint()
            }
        }

    }

    class HeightRestrictedJPanel: JPanel() {
        override fun getMaximumSize(): Dimension {
            val pref: Dimension = getPreferredSize();
            return Dimension(Integer.MAX_VALUE, pref.height);
        }
    }

    public class DetailsTab: JPanel() {

        var classificationDescription: JTextField = JTextField("",25)
        var complexField: JTextField = JTextField("",12)
        var complexFieldDescription: JTextField = JTextField("",25)
        var entityField: JTextField = JTextField("",12)
        var entityFieldDescription: JTextField = JTextField("",25)
        var spaceField: JTextField = JTextField("",12)
        var spaceFieldDescription: JTextField = JTextField("",25)

        override fun addNotify() {
            super.addNotify()
            layout = BorderLayout()

            classificationDescription.isEditable = false
            complexField.isEditable = false
            complexFieldDescription.isEditable = false
            entityField.isEditable = false
            entityFieldDescription.isEditable = false
            spaceField.isEditable = false
            spaceFieldDescription.isEditable = false

            val classificationPanel = HeightRestrictedJPanel()
            classificationPanel.layout = BorderLayout()
            classificationPanel.border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Uniclass Location Classification")

            val classificationFieldsPanel = JPanel()
            val complexLabel: JLabel = JLabel("Complex (Co)")
            val entityLabel: JLabel = JLabel("Entity (En)")
            val spaceLocationLabel: JLabel = JLabel("Space/Location (SL)")

            classificationFieldsPanel.layout = GridLayout(3,3)

            classificationFieldsPanel.add(complexLabel)
            classificationFieldsPanel.add(complexField)
            classificationFieldsPanel.add(complexFieldDescription)

            classificationFieldsPanel.add(entityLabel)
            classificationFieldsPanel.add(entityField)
            classificationFieldsPanel.add(entityFieldDescription)

            classificationFieldsPanel.add(spaceLocationLabel)
            classificationFieldsPanel.add(spaceField)
            classificationFieldsPanel.add(spaceFieldDescription)
            classificationPanel.add(classificationDescription, BorderLayout.NORTH)
            classificationPanel.add(classificationFieldsPanel, BorderLayout.WEST)

            add(classificationPanel, BorderLayout.NORTH)

        }
    }
}

