package com.ventia.entity.classification

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@SequenceGenerator(name = "attribute-set-id-seq", sequenceName = "ATTRIBUTE_SET_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "ATTRIBUTE_SET")
data class AttributeSetEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute-set-id-seq")
    @Column(nullable = false)
    val id: Long = -1,

    @Column(nullable = false, unique = true, length = 12)
    val key: String = "key",

)
