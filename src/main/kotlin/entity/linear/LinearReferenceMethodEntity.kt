package com.ventia.entity.linear

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class LinearReferenceMethodEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset-id-seq")
    @Column(nullable = false)
    val id: Long = -1,

)
