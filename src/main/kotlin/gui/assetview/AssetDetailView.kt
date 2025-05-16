package com.ventia.gui.assetview

import com.ventia.controller.AssetController
import com.ventia.entity.classification.*
import com.ventia.gui.assetview.intent.AssetSelectedIntent
import com.ventia.gui.assetview.intent.LocationSelectedIntent
import com.ventia.gui.assetview.intent.RootSelectedIntent
import com.ventia.gui.assetview.intent.SystemSelectedIntent
import com.ventia.intent.Intent
import com.ventia.intent.IntentHub
import com.ventia.intent.IntentReceiver
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

const val COMPLEX_LABEL = "Complex (Co):"
const val ENTITY_LABEL = "Entity (En):"
const val SPACE_LOCATION_LABEL = "Space/Location (SL):"
const val ELEMENT_FUNCTION_LABEL = "Element/Function (EF):"
const val SYSTEM_LABEL = "System (Ss):"
const val PRODUCT_LABEL = "Product (Pr):"


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
        detailsTab.classificationDescription.text = ""
        detailsTab.classificationPath.text = ""
        detailsTab.complexField.text = ""
        detailsTab.complexFieldDescription.text = ""
        detailsTab.entityField.text = ""
        detailsTab.entityFieldDescription.text = ""
        detailsTab.spaceField.text = ""
        detailsTab.spaceFieldDescription.text = ""

        if(intent is LocationSelectedIntent) {


            if(intent.location.classification != null) {
                val classification: ClassificationEntity = intent.location.classification
                detailsTab.classificationDescription.text = classification.toString()
                detailsTab.classificationPath.text = classification.getPathString()
                if(classification is ClassificationComplexEntity) {
                    detailsTab.complexField.text = classification.complex?.code
                    detailsTab.complexFieldDescription.text = classification.complex?.title
                } else if(classification is ClassificationComplexEntityLocationEntity) {
                    detailsTab.titledBorder.title = "Uniclass Location Classification"

                    detailsTab.l1Label.text = COMPLEX_LABEL
                    detailsTab.complexField.text = classification.complex?.code
                    detailsTab.complexFieldDescription.text = classification.complex?.title

                    detailsTab.l2label.text = ENTITY_LABEL
                    detailsTab.entityField.text = classification.entity?.code
                    detailsTab.entityFieldDescription.text = classification.entity?.title

                    detailsTab.l3label.text = SPACE_LOCATION_LABEL
                    detailsTab.spaceField.text = classification.spaceLocation?.code
                    detailsTab.spaceFieldDescription.text = classification.spaceLocation?.title
                } else if(classification is ClassificationManagedAssetEntity) {
                    detailsTab.titledBorder.title = "Uniclass Asset (Managed) Classification"

                    detailsTab.l1Label.text = ELEMENT_FUNCTION_LABEL
                    detailsTab.complexField.text = classification.function?.code
                    detailsTab.complexFieldDescription.text = classification.function?.title

                    detailsTab.l2label.text = SYSTEM_LABEL
                    detailsTab.entityField.text = classification.system?.code
                    detailsTab.entityFieldDescription.text = classification.system?.title

                    detailsTab.l3label.text = PRODUCT_LABEL
                    detailsTab.spaceField.text = classification.product?.code
                    detailsTab.spaceFieldDescription.text = classification.product?.title
                }
            }
        } else if (intent is AssetSelectedIntent) {
            if(intent.asset.classification != null) {
                val classification: ClassificationEntity = intent.asset.classification
                detailsTab.classificationDescription.text = classification.toString()
                detailsTab.classificationPath.text = classification.getPathString()
                if(classification is ClassificationComplexEntityLocationEntity) {

                    detailsTab.titledBorder.title = "Uniclass Asset (Linear) Classification"
                    detailsTab.l1Label.text = COMPLEX_LABEL
                    detailsTab.complexField.text = classification.complex?.code
                    detailsTab.complexFieldDescription.text = classification.complex?.title

                    detailsTab.l2label.text = ENTITY_LABEL
                    detailsTab.entityField.text = classification.entity?.code
                    detailsTab.entityFieldDescription.text = classification.entity?.title

                    detailsTab.l3label.text = SPACE_LOCATION_LABEL
                    detailsTab.spaceField.text = classification.spaceLocation?.code
                    detailsTab.spaceFieldDescription.text = classification.spaceLocation?.title
                } else if (classification is ClassificationAssetEntity) {
                    detailsTab.titledBorder.title = "Uniclass Asset (Product) Classification"
                    detailsTab.classificationDescription.text = classification.toString()
                    detailsTab.classificationPath.text = classification.getPathString()
                }


            }

        }
        detailsTab.repaint()
    }

//    class HeightRestrictedJPanel: JPanel() {
//        override fun getMaximumSize(): Dimension {
//            val pref: Dimension = getPreferredSize();
//            return Dimension(Integer.MAX_VALUE, pref.height);
//        }
//    }

    public class DetailsTab: JPanel() {

        val descriptionLength = 32
        val codeLength = 15
        val codeDescriptionLength = 28

        var classificationDescription: JTextField = JTextField(descriptionLength)
        var classificationPath: JTextField = JTextField(descriptionLength)
        var complexField: JTextField = JTextField(codeLength)
        var complexFieldDescription: JTextField = JTextField(codeDescriptionLength)
        var entityField: JTextField = JTextField("",codeLength)
        var entityFieldDescription: JTextField = JTextField("",codeDescriptionLength)
        var spaceField: JTextField = JTextField("",codeLength)
        var spaceFieldDescription: JTextField = JTextField("",codeDescriptionLength)

        val descriptionLabel: JLabel = JLabel("T2D Description:", SwingConstants.RIGHT)
        val pathLabel: JLabel = JLabel("AMIS Classpath:", SwingConstants.RIGHT)
        val l1Label: JLabel = JLabel(COMPLEX_LABEL, SwingConstants.RIGHT)
        val l2label: JLabel = JLabel(ENTITY_LABEL, SwingConstants.RIGHT)
        val l3label: JLabel = JLabel(SPACE_LOCATION_LABEL, SwingConstants.RIGHT)

        val titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Uniclass Location Classification")

        override fun addNotify() {
            super.addNotify()
            layout = BorderLayout()

            classificationDescription.isEditable = false
            classificationPath.isEditable = false
            complexField.isEditable = false
            complexFieldDescription.isEditable = false
            entityField.isEditable = false
            entityFieldDescription.isEditable = false
            spaceField.isEditable = false
            spaceFieldDescription.isEditable = false

            val classificationPanel = JPanel()
            classificationPanel.layout = BorderLayout()
            classificationPanel.border = titledBorder

            val classificationFieldsPanel = JPanel()

            val classificationLayout = GridBagLayout()
            val gbc = GridBagConstraints()
            classificationFieldsPanel.layout = classificationLayout

            var row = 0
            gbc.insets = Insets(2, 2, 2, 2)
            gbc.gridx = 0
            gbc.gridy = row
            gbc.gridwidth = 1
            gbc.gridheight = 1
            gbc.anchor = GridBagConstraints.WEST
            classificationFieldsPanel.add(descriptionLabel, gbc)
            gbc.gridx = 1
            gbc.gridwidth = 4
            classificationFieldsPanel.add(classificationDescription, gbc)

            row++
            gbc.gridx = 0
            gbc.gridy = row
            gbc.gridwidth = 1
            gbc.gridheight = 1
            classificationFieldsPanel.add(pathLabel, gbc)
            gbc.gridx = 1
            gbc.gridwidth = 4
            classificationFieldsPanel.add(classificationPath, gbc)


            row++
            gbc.gridx = 0
            gbc.gridy = row
            gbc.gridwidth = 1
            gbc.gridheight = 1
            classificationFieldsPanel.add(l1Label, gbc)
            gbc.gridx = 1
            classificationFieldsPanel.add(complexField, gbc)
            gbc.gridx = 2
            classificationFieldsPanel.add(complexFieldDescription, gbc)

            row++
            gbc.gridx = 0
            gbc.gridy = row
            classificationFieldsPanel.add(l2label, gbc)
            gbc.gridx = 1
            classificationFieldsPanel.add(entityField, gbc)
            gbc.gridx = 2
            classificationFieldsPanel.add(entityFieldDescription, gbc)

            row++
            gbc.gridx = 0
            gbc.gridy = row
            classificationFieldsPanel.add(l3label, gbc)
            gbc.gridx = 1
            classificationFieldsPanel.add(spaceField, gbc)
            gbc.gridx = 2
            classificationFieldsPanel.add(spaceFieldDescription, gbc)

            classificationPanel.add(classificationFieldsPanel, BorderLayout.WEST)

            add(classificationPanel, BorderLayout.NORTH)

        }

    }

}


