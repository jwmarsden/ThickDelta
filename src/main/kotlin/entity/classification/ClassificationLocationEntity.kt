package com.ventia.entity.classification

import com.ventia.entity.classification.UniclassSpaceLocationEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "CLASSIFICATION_LOCATION")
class ClassificationLocationEntity: ClassificationEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceLocation", referencedColumnName = "code")
    val spaceLocation: UniclassSpaceLocationEntity? = null

    override fun getPathString(): String {
        return spaceLocation?.code ?: "Un"
    }

}