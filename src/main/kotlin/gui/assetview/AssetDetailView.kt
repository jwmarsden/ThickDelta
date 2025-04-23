package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.entity.classification.ClassificationComplexEntity
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
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.GridLayout
import java.awt.Insets
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
        //println("AssetDetailView received intent of $intent")

        if(intent is LocationSelectedIntent) {
            detailsTab.classificationDescription.text = ""
            detailsTab.complexField.text = ""
            detailsTab.complexFieldDescription.text = ""
            detailsTab.entityField.text = ""
            detailsTab.entityFieldDescription.text = ""
            detailsTab.spaceField.text = ""
            detailsTab.spaceFieldDescription.text = ""

            if(intent.location.classification != null) {
                val classification: ClassificationEntity = intent.location.classification
                //println(classification.getPathString())
                detailsTab.classificationDescription.text = classification.toString()
                if(classification is ClassificationComplexEntity) {
                    detailsTab.complexField.text = classification.complex?.code
                    detailsTab.complexFieldDescription.text = classification.complex?.title
                } else if(classification is ClassificationComplexEntityLocationEntity) {
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

//    class HeightRestrictedJPanel: JPanel() {
//        override fun getMaximumSize(): Dimension {
//            val pref: Dimension = getPreferredSize();
//            return Dimension(Integer.MAX_VALUE, pref.height);
//        }
//    }

    public class DetailsTab: JPanel() {

        var classificationDescription: JTextField = JTextField(29)
        var complexField: JTextField = JTextField(8)
        var complexFieldDescription: JTextField = JTextField(20)
        var entityField: JTextField = JTextField("",8)
        var entityFieldDescription: JTextField = JTextField("",20)
        var spaceField: JTextField = JTextField("",8)
        var spaceFieldDescription: JTextField = JTextField("",20)

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

            val classificationPanel = JPanel()
            classificationPanel.layout = BorderLayout()
            classificationPanel.border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Uniclass Location Classification")

            val classificationFieldsPanel = JPanel()
            val descriptionLabel: JLabel = JLabel("T2D Description:", SwingConstants.RIGHT)

            val complexLabel: JLabel = JLabel("Complex (Co):", SwingConstants.RIGHT)
            //complexLabel.size = Dimension(100, 20)
            val entityLabel: JLabel = JLabel("Entity (En):")
            val spaceLocationLabel: JLabel = JLabel("Space/Location (SL):")

            val classificationLayout = GridBagLayout()
            val gbc = GridBagConstraints()
            classificationFieldsPanel.layout = classificationLayout

            gbc.insets = Insets(2, 2, 2, 2)
            gbc.gridx = 0
            gbc.gridy = 0
            gbc.gridwidth = 1
            gbc.gridheight = 1
            gbc.anchor = GridBagConstraints.WEST
            classificationFieldsPanel.add(descriptionLabel, gbc)
            gbc.gridx = 1
            gbc.gridwidth = 4
            classificationFieldsPanel.add(classificationDescription, gbc)

            gbc.gridx = 0
            gbc.gridy = 1
            gbc.gridwidth = 1
            gbc.gridheight = 1
            classificationFieldsPanel.add(complexLabel, gbc)
            gbc.gridx = 1
            classificationFieldsPanel.add(complexField, gbc)
            gbc.gridx = 2
            classificationFieldsPanel.add(complexFieldDescription, gbc)
            gbc.gridx = 0
            gbc.gridy = 2
            classificationFieldsPanel.add(entityLabel, gbc)
            gbc.gridx = 1
            classificationFieldsPanel.add(entityField, gbc)
            gbc.gridx = 2
            classificationFieldsPanel.add(entityFieldDescription, gbc)
            gbc.gridx = 0
            gbc.gridy = 3
            classificationFieldsPanel.add(spaceLocationLabel, gbc)
            gbc.gridx = 1
            classificationFieldsPanel.add(spaceField, gbc)
            gbc.gridx = 2
            classificationFieldsPanel.add(spaceFieldDescription, gbc)

            classificationPanel.add(classificationFieldsPanel, BorderLayout.WEST)

            add(classificationPanel, BorderLayout.NORTH)

        }
    }
}

