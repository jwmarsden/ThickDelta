package com.ventia.model

import com.ventia.entities.LocationEntity
import org.hibernate.SessionFactory
import org.hibernate.query.Query
import java.util.*


class AssetModel(sessionFactory: SessionFactory) : Model(sessionFactory) {

    private var loaded: Boolean = false
    private val rootLocationIdentifiers = arrayOf("T2D")
    private var rootsLocations: MutableList<LocationEntity> = mutableListOf()

    fun lookupLocationRoots(): MutableList<LocationEntity> {
        val session = sessionFactory.openSession()
        if (loaded) {
            return rootsLocations
        } else {
            rootsLocations = mutableListOf()
            val inValue = StringBuilder()
            for (i in 0..<rootLocationIdentifiers.size - 1) {
                inValue.append("'").append(rootLocationIdentifiers[i]).append("',")
            }
            inValue.append("'").append(rootLocationIdentifiers[rootLocationIdentifiers.size - 1]).append("'")

            val query: Query<LocationEntity> = session.createQuery("from LocationEntity as location where location.key in ($inValue)", LocationEntity::class.java)
            val rootLocationEntities: List<LocationEntity> = query.list()
            for (loc in rootLocationEntities) {
                rootsLocations.add(loc)
            }
            loaded = true
            return rootsLocations
        }
    }
}