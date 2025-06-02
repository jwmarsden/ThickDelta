package com.ventia.entities

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy

@Entity
@SequenceGenerator(name = "location_status-id-seq", sequenceName = "LOCATION_STATUS_ID_SEQ", initialValue = 100, allocationSize = 10)
@Table(name = "LOCATION_STATUS")
data class LocationStatusEntity (

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
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass = this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as LocationStatusEntity

        return id != null && id == other.id
    }

    final override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  systemStatus = $systemStatus   ,   status = $status )"
    }
}
