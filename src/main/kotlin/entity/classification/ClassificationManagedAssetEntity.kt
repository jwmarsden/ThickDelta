package com.ventia.entity.classification

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "CLASSIFICATION_ASSET_MANAGED")
class ClassificationManagedAssetEntity: ClassificationEntity() {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELEMENT_FUNCTION", referencedColumnName = "code")
    val function: UniclassElementFunctionEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system", referencedColumnName = "code")
    val system: UniclassSystemEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", referencedColumnName = "code")
    val product: UniclassProductEntity? = null

    override fun getPathString(): String {
        return "${function?.code} / ${system?.code} / ${product?.code}"
    }
}