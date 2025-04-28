package com.ventia.entity.classification

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "CLASSIFICATION_ASSET")
class ClassificationAssetEntity: ClassificationEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", referencedColumnName = "code")
    val product: UniclassProductEntity? = null

    override fun getPathString(): String {
        return "${product?.code}"
    }
}