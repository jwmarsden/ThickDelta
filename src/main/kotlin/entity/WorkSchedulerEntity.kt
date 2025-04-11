package com.ventia.entity

import com.ventia.entities.LocationEntity
import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "work-scheduler-id-seq", sequenceName = "WORK_SCHEDULER_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "WORK_SCHEDULER")
data class WorkSchedulerEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work-scheduler-id-seq")
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
        other as WorkSchedulerEntity
        return key == other.key
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    enum class TYPE {
        CYCLICAL,
        DATE_LIST,
        METER_BASED
    }
}

