
package com.ventia.intent

class IntentRegistry {
    private val registry: MutableList<Class<out Intent>> = ArrayList()

    fun registerIntent(intentClass: Class<out Intent>): Int {
        synchronized(registry) {
            if (registry.contains(intentClass)) {
                return registry.indexOf(intentClass)
            }
            registry.add(intentClass)
            return registry.indexOf(intentClass)
        }
    }

    fun registerIntent(intent: Intent): Int {
        val intentClass: Class<out Intent> = intent.javaClass
        return registerIntent(intentClass)
    }

    fun lookupIntent(intentClass: Class<out Intent>): Int {
        return registerIntent(intentClass)
    }

    companion object {
        private var singleton: IntentRegistry?
        init {
            singleton = null
        }
        fun lookup(): IntentRegistry {
            if (singleton == null) {
                singleton = IntentRegistry()
            }
            return singleton!!
        }
    }
}