package com.ventia.gui.assetview.intent

import com.ventia.controller.Controller
import com.ventia.entities.LocationEntity
import com.ventia.intent.Intent

class LocationSelectedIntent (source: Controller, val location: LocationEntity) : Intent(source) {
    override fun toString(): String {
        return "LocationSelectedIntent(location=$location)"
    }
}