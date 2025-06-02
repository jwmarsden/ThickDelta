package com.ventia.data.locations

data class LocationTransferObject(
    val identifier: String,

    val hashTag: String,
    var parentHashTag: String? = null,

    val key: String,
    var description: String? = null,
    var type: String? = null,
    var status: String? = null,
    val maintainable: Boolean = false,

    var parentIdentifier: String? = null,

    var uniclassCo: String? = null,
    var uniclassEn: String? = null,
    var uniclassSL: String? = null,

    var groupCode: String? = null,
    var sortOrder: Int = 100,
)