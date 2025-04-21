package com.ventia.entity.classification

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "CLASSIFICATION_SPACE_LOCATION")
class ClassificationSpaceLocationEntity: ClassificationEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPACE_LOCATION", referencedColumnName = "code")
    val spaceLocation: UniclassSpaceLocationEntity? = null

    override fun getPathString(): String {
        return "${spaceLocation?.code}"
    }

}