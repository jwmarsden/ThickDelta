
package com.ventia.intent

class IntentHub {
    private val listenerRegistry: MutableMap<Class<out Intent>, MutableList<IntentReceiver>> = mutableMapOf()

    fun registerForIntent(listener: IntentReceiver, intent: Intent) {
        registerForIntent(listener, intent::class.java)
    }

    fun registerForIntent(listener: IntentReceiver, intentClass: Class<out Intent?>) {
        var listenerList: MutableList<IntentReceiver>? = listenerRegistry[intentClass]
        if (listenerList == null) {
            listenerList = mutableListOf()
            listenerRegistry[intentClass] = listenerList
        }
        if (!listenerList.contains(listener)) {
            listenerList.add(listener)
        }
    }

    fun deregisterForIntent(listener: IntentReceiver, intentClass: Class<out Intent?>) {
        val listenerList: MutableList<IntentReceiver>? = listenerRegistry[intentClass]
        if (listenerList != null && listenerList.contains(listener)) {
            listenerList.remove(listener)
        }
    }

    fun notifyIntent(intent: Intent) {
        val listenerList: List<IntentReceiver>? = listenerRegistry[intent::class.java]
        if (listenerList != null) {
            for (receiver in listenerList) {
                receiver.receiveIntent(intent)
                intent.addReceiver(receiver)
            }
        }
    }


    companion object {
        private var singleton: IntentHub?
        init {
            singleton = null
        }
        fun lookup(): IntentHub {
            if (singleton == null) {
                singleton = IntentHub()
            }
            return singleton!!
        }
    }
}