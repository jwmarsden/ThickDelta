package com.ventia.entity

import jakarta.persistence.*

@Entity
@SequenceGenerator(name = "type-id-seq", sequenceName = "TYPE_ID_SEQ", initialValue = 20, allocationSize = 10)
@Table(name = "TYPE")
data class TypeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type-id-seq")
    @Column(nullable = false)
    val id: Long? = -1,

    @Column(nullable = false, length = 10, unique = true)
    val type: String? = null,

    @Column(nullable = true, length = 150)
    val description: String? = null,

    @Column(nullable = false)
    val active: Boolean = false,
) {
    override fun toString(): String {
        return "$type"
    }
}
