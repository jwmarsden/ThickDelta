package com.ventia.entity.classification

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "CLASSIFICATION_COMPLEX")
class ClassificationComplexEntity: ClassificationEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complex", referencedColumnName = "code")
    val complex: UniclassComplexEntity? = null

    override fun getPathString(): String {
        return "${complex?.code}"
    }

}

