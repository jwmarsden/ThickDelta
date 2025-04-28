package com.ventia.entity.activity

import com.ventia.entities.LocationEntity
import com.ventia.entity.AssetEntity
import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "activity-schedule-id-seq", sequenceName = "ACTIVITY_SCHEDULER_ID_SEQ", initialValue = 100, allocationSize = 10)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class ActivityScheduleEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity-schedule-id-seq")
    //@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    val id: Long = -1,

    @Column(nullable = false, unique = true, length = 12)
    val key: String = "key",

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    val type: TYPE? = null,

    @Column(length = 50)
    val description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location", referencedColumnName = "key", nullable = true)
    val location: LocationEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset", referencedColumnName = "key", nullable = true)
    val asset: AssetEntity? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ActivityScheduleEntity
        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    enum class TYPE {
        CYCLICAL,
        METER_BASED,
        DATE_LIST,
    }
}

