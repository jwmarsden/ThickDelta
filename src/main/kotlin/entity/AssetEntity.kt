package com.ventia.entity

import com.ventia.entities.DisciplineEntity
import com.ventia.entities.LocationEntity
import jakarta.persistence.*
@Entity
@SequenceGenerator(name = "asset-id-seq", sequenceName = "ASSET_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "ASSET")
data class AssetEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset-id-seq")
    @Column(nullable = false)
    val id: Long = -1,

    @Column(nullable = false, unique = true, length = 12)
    val key: String = "key",

    @Column(length = 50)
    val description: String? = null,

    @Column(nullable = false)
    val linear: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", referencedColumnName = "key")
    val parent: AssetEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location", referencedColumnName = "key")
    val location: LocationEntity? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="parent", referencedColumnName = "key")
    val children: List<AssetEntity> = mutableListOf(),

    ) {
    override fun toString(): String {
        return "AssetEntity(key='$key', description=$description)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AssetEntity

        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

}
