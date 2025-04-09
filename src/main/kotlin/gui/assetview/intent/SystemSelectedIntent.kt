package com.ventia.gui.assetview.intent

import com.ventia.controller.Controller
import com.ventia.entities.SystemEntity
import com.ventia.intent.Intent

class SystemSelectedIntent(source: Controller, val system: SystemEntity) : Intent(source) {
}