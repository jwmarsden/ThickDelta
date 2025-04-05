package com.ventia.entities

import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "location-id-seq", sequenceName = "LOCATION_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "LOCATION")
data class LocationEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location-id-seq")
    @Column(nullable = false)
    val id: Long = -1,

    @Column(nullable = false, unique = true)
    val key: String = "key",

    val description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "status", nullable = false)
    val status: LocationStatusEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline", referencedColumnName = "discipline")
    val discipline: DisciplineEntity? = null,

    @ManyToMany(mappedBy = "children", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val parents: Set<LocationEntity> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "LOCATION_SYSTEM_HIERARCHY",
        joinColumns = [JoinColumn(name = "parent_id")],
        inverseJoinColumns = [JoinColumn(name = "location_id")],
    )
    val children: Set<LocationEntity> = setOf(),

    ) {

    override fun toString(): String {
        return "LocationEntity(id=$id, key=$key, description=$description)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationEntity

        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    @Suppress("unused")
    fun hasParent(@Suppress("UNUSED_PARAMETER") system: SystemEntity): Boolean {
        return false
    }


}
