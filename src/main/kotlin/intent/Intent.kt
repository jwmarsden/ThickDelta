package com.ventia.intent

import com.ventia.controller.Controller

abstract class Intent(private val source: Controller) {
    private var receivers: MutableList<IntentReceiver>?
    private val topic: Int

    init {
        this.receivers = null
        this.topic = IntentRegistry.lookup().registerIntent(this)
    }

    fun addReceiver(component: IntentReceiver) {
        if (receivers == null) {
            receivers = mutableListOf()
        }
        receivers!!.add(component)
    }

    fun wasReceived(): Boolean {
        return receivers != null && receivers!!.isNotEmpty()
    }

    override fun toString(): String {
        return "Intent(source=$source, topic=$topic)"
    }
}