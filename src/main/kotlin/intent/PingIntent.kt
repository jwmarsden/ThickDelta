package com.ventia.intent

import com.ventia.controller.Controller

class PingIntent(private val source: Controller) : Intent(source) {
    override fun toString(): String {
        return "Ping($source)"
    }
}