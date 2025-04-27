package com.ventia.entity.classification

import jakarta.persistence.*

@Entity
@Table(name = "CLASSIFICATION_COMPLEX_ENTITY_LOCATION")
class ClassificationComplexEntityLocationEntity: ClassificationEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complex", referencedColumnName = "code")
    val complex: UniclassComplexEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity", referencedColumnName = "code", nullable = true)
    val entity: UniclassEntityEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPACE_LOCATION", referencedColumnName = "code", nullable = true)
    val spaceLocation: UniclassSpaceLocationEntity? = null

    override fun getPathString(): String {
        return buildString {
            if (complex != null) {
                append("${complex.code}")
                if(entity != null || spaceLocation != null) {
                    append(" / ")
                }
            }
            if (entity != null) {
                append("${entity.code}")
                if(spaceLocation != null) {
                    append(" / ")
                }
            }
            if (spaceLocation != null) {
                append("${spaceLocation.code}")
            }
        }
    }
}