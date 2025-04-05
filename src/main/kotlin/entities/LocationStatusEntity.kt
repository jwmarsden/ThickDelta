package com.ventia.entities

import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "location_status-id-seq", sequenceName = "LOCATION_STATUS_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "LOCATION_STATUS")
data class LocationStatusEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_status-id-seq")
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
