package com.ventia.entity.classification

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "CLASSIFICATION_ENTITY")
class ClassificationEntityEntity: ClassificationEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity", referencedColumnName = "code")
    val entity: UniclassEntityEntity? = null

    override fun getPathString(): String {
        return "${entity?.code}"
    }
}