package com.ventia.entity.classification

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import org.hibernate.proxy.HibernateProxy

@Entity
@SequenceGenerator(name = "uniclass-entity-id-seq", sequenceName = "UNICLASS_ENTITY_ID_SEQ", initialValue = 200, allocationSize = 10)
@Table(name = "UNICLASS_ENTITY")
data class UniclassEntityEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uniclass-entity-id-seq")
    @Column(nullable = false)
    val id: Long? = -1,

    @Column(nullable = false, length = 15, unique = true)
    val code: String? = null,

    @Column(nullable = false, name = "GROUP_CODE", length = 2)
    val groupCode: String? = null,
    @Column(nullable = true, name = "SUB_GROUP_CODE", length = 2)
    val subGroupCode: String? = null,
    @Column(nullable = true, name = "SECTION_CODE", length = 2)
    val sectionCode: String? = null,
    @Column(nullable = true, name = "OBJECT_CODE", length = 2)
    val objectCode: String? = null,

    @Column(nullable = true, length = 150)
    val title: String? = null,

    @Column(nullable = false)
    val active: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass = this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as UniclassEntityEntity

        return id != null && id == other.id
    }

    final override fun hashCode(): Int =
        javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  code = $code )"
    }
}