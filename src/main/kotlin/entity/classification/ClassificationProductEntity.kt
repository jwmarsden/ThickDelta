package com.ventia.entity.classification

import com.ventia.entity.classification.UniclassElementFunctionEntity
import jakarta.persistence.Entity
import com.ventia.entity.classification.UniclassProductEntity
import com.ventia.entity.classification.UniclassSystemEntity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "CLASSIFICATION_PRODUCT")
class ClassificationProductEntity: ClassificationEntity() {


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