package com.ventia.controller

import com.ventia.entities.LocationEntity
import com.ventia.model.AssetModel
import com.ventia.model.Model

class AssetController(override val model: AssetModel) : Controller(model) {

    fun lookupLocationRoots(): MutableList<LocationEntity>? {
        return model.lookupLocationRoots()
    }
}