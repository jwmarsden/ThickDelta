package com.ventia.entity.classification

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "classification-id-seq", sequenceName = "CLASSIFICATION_ID_SEQ", initialValue = 200, allocationSize = 10)
@Table(name = "CLASSIFICATION")
abstract class ClassificationEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classification-id-seq")
    @Column(nullable = false)
    val id: Long? = -1,

) {

    abstract fun getPathString(): String

    override fun toString(): String {
        return "ClassificationEntity(id=$id)"
    }
}