package dto

import com.ventia.dto.TypeDto
import java.io.Serializable

/**
 * DTO for {@link com.ventia.entities.LocationEntity}
 */
data class LocationDto(
    val id: Long = -1,
    val key: String = "key",
    val description: String? = null,
    val status: LocationStatusDto? = null,
    val type: TypeDto? = null,
    val maintainableFlag: Boolean = false
) : Serializable {
    /**
     * DTO for {@link com.ventia.entities.LocationStatusEntity}
     */
    data class LocationStatusDto(
        val id: Long? = -1,
        val systemStatus: String? = null,
        val status: String? = null,
        val description: String? = null,
        val active: Boolean = false
    ) : Serializable
}