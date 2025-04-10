package com.ventia.gui.assetview.intent

import com.ventia.controller.Controller
import com.ventia.entities.LocationEntity
import com.ventia.intent.Intent

class RootSelectedIntent (source: Controller, val location: LocationEntity) : Intent(source) {
}