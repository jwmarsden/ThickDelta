package com.ventia.entity.classification

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "classification-id-seq", sequenceName = "CLASSIFICATION_ID_SEQ", initialValue = 200, allocationSize = 10)
@Table(name = "CLASSIFICATION")
abstract class ClassificationEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classification-id-seq")
    @Column(nullable = false)
    open val id: Long? = -1,

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    open val type: ClassificationType = ClassificationType.UNKNOWN,

    @Column(length = 150, nullable = false)
    open val description: String = "Unknown",

) {

    abstract fun getPathString(): String

    override fun toString(): String {
        return description
    }

    enum class ClassificationType {
        UNKNOWN,
        COMPLEX,
        LOCATION,
        MANAGED_ASSET,
        ASSET,
        LINEAR,
        LINEAR_FEATURE,
        LINEAR_RELATIONSHIP
    }

}