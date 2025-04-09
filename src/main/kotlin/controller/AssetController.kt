package com.ventia.controller

import com.ventia.entities.LocationEntity
import com.ventia.entities.SystemEntity
import com.ventia.model.AssetModel

class AssetController(override val model: AssetModel) : Controller(model) {

    fun getSystemList(): List<SystemEntity> {
        return model.getSystemList()
    }

    fun lookupLocationRoots(system: SystemEntity): List<LocationEntity> {
        return model.lookupLocationRoots(system)
    }

    fun getChildrenInSystem(parent: LocationEntity, system: SystemEntity): List<LocationEntity> {
        return model.getChildrenInSystem(parent, system)
    }

    fun getChildrenInSystem(parent: LocationEntity, systemString: String): List<LocationEntity> {
        return model.getChildrenInSystem(parent, systemString)
    }

    fun getParentInSystem(child: LocationEntity, system: SystemEntity): LocationEntity {
        return model.getParentInSystem(child , system)
    }

    fun getParentInSystem(child: LocationEntity, systemString: String): LocationEntity {
        return model.getParentInSystem(child, systemString)
    }

}