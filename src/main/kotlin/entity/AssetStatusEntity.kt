package com.ventia.entity

import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "asset_status-id-seq", sequenceName = "ASSET_STATUS_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "ASSET_STATUS")
data class AssetStatusEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset_status-id-seq")
    @Column(nullable = false)
    val id: Long? = -1,

    @Column(nullable = false, name = "SYSTEM_STATUS")
    var systemStatus: String? = null,

    @Column(nullable = false)
    var status: String? = null,

    val description: String? = null,

    @Column(nullable = false)
    val active: Boolean = false,
)
