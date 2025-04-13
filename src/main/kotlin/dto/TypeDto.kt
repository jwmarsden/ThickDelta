package com.ventia.dto

import java.io.Serializable

/**
 * DTO for {@link com.ventia.entity.TypeEntity}
 */
data class TypeDto(
    val id: Long? = -1,
    val type: String? = null,
    val description: String? = null,
    val active: Boolean = false
) : Serializable