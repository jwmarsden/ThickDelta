package com.ventia.entities

import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "system-id-seq", sequenceName = "SYSTEM_ID_SEQ", initialValue = 20, allocationSize = 10)
@Table(name = "SYSTEM")
data class SystemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system-id-seq")
    @Column(nullable = false)
    val id: Long? = -1,

    @Column(nullable = false)
    val system: String? = null,

    val description: String? = null,

    @Column(nullable = false)
    val graph: Boolean = false,

    @Column(nullable = false)
    val active: Boolean = false,

) {
    override fun toString(): String {
        return system.toString()
    }
}
