package com.ventia.entities

import com.ventia.entity.AssetEntity
import com.ventia.entity.TypeEntity
import com.ventia.entity.classification.ClassificationEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLJoinTableRestriction
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.Where
import org.hibernate.annotations.WhereJoinTable
import org.hibernate.property.access.spi.Getter
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

    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "status", nullable = false)
    val status: LocationStatusEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline", referencedColumnName = "discipline")
    val discipline: DisciplineEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "type", nullable = true, columnDefinition = "VARCHAR(10) default 'UNK'")
    val type: TypeEntity? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    val classification: ClassificationEntity? = null,

    @Column(nullable = false, name = "MAINTAINABLE_FLAG", columnDefinition="BOOLEAN DEFAULT FALSE")
    val maintainableFlag: Boolean = false,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="parent_id", referencedColumnName = "key")
    var childrenFromAllSystems: List<LocationSystemHierarchyEntity>? = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="location_id", referencedColumnName = "key")
    var parentsFromAllSystems: List<LocationSystemHierarchyEntity>? = mutableListOf(),

    /*
    @ManyToMany(mappedBy = "children", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @SQLJoinTableRestriction("system_id in (select sys.id from system sys where sys.system = 'DEFAULT')")
    val primaryParent: List<LocationEntity> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "LOCATION_SYSTEM_HIERARCHY",
        joinColumns = [JoinColumn(name = "parent_id")],
        inverseJoinColumns = [JoinColumn(name = "location_id")],
    )
    val children: List<LocationEntity> = mutableListOf(),
     */
    @Transient
    val children: List<LocationEntity> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="location", referencedColumnName = "key")
    val assets: List<AssetEntity> = mutableListOf(),

    ) {


    val level: Int
        get() {
            return countLevels()
        }

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

    private fun countLevels(): Int {
        var level = this
        var count = 0
//        while(level.primaryParent.isNotEmpty()) {
//            level = level.primaryParent[0]
//            count++
//        }
        return count
    }
}
