package com.ventia.entities

import com.ventia.entity.AssetEntity
import jakarta.persistence.*
import org.hibernate.query.Query

@Entity
@SequenceGenerator(name = "location-id-seq", sequenceName = "LOCATION_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "LOCATION")
data class LocationEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location-id-seq")
    @Column(nullable = false)
    val id: Long = -1,

    @Column(nullable = false, unique = true, length = 12)
    val key: String = "key",

    val description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "status", nullable = false)
    val status: LocationStatusEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline", referencedColumnName = "discipline")
    val discipline: DisciplineEntity? = null,

    @Column(nullable = false, name = "MAINTAINABLE_FLAG", columnDefinition="BOOLEAN DEFAULT FALSE")
    val maintainableFlag: Boolean = false,

    //@ManyToMany(mappedBy = "children", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    //val parents: List<LocationEntity> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "LOCATION_SYSTEM_HIERARCHY",
        joinColumns = [JoinColumn(name = "parent_id")],
        inverseJoinColumns = [JoinColumn(name = "location_id")],
    )
    val children: List<LocationEntity> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="location", referencedColumnName = "key")
    val assets: List<AssetEntity> = mutableListOf(),

    ) {

    fun getAssetsWithoutParent(): List<AssetEntity> {
        val assetWithoutParent: MutableList<AssetEntity> = mutableListOf()
        if (assets.isNotEmpty()) {
            for (asset in assets) {
                if (!asset.hasParent()) {
                    assetWithoutParent.add(asset)
                }
            }
        }
        return assetWithoutParent
    }

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
}
