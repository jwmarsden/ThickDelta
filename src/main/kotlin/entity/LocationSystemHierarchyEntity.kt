package com.ventia.entities

import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "location_system_hierarchy-id-seq", sequenceName = "LOCATION_SYSTEM_HIERARCHY_ID_SEQ", initialValue = 10, allocationSize = 10)
@Table(name = "LOCATION_SYSTEM_HIERARCHY",
    uniqueConstraints=[UniqueConstraint(name= "LOCATION_SYSTEM_HIERARCHY_C01", columnNames = ["SYSTEM_ID", "PARENT_ID","LOCATION_ID"])]
)
data class LocationSystemHierarchyEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_system_hierarchy-id-seq")
    @Column(nullable = false)
    val id: Long? = -1,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val system: SystemEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val parent: LocationEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val location: LocationEntity? = null,

    @Column(nullable = false, columnDefinition = "int default 100")
    val order: Int

) {
    override fun toString(): String {
        return "LocationSystemHierarchyEntity(id=$id)"
    }
}
