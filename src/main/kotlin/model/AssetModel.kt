package com.ventia.model

import com.ventia.entities.LocationEntity
import com.ventia.entities.SystemEntity
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.query.Query

class AssetModel(sessionFactory: SessionFactory) : Model(sessionFactory) {

    private var loaded: Boolean = false
    private val rootLocationIdentifiers = arrayOf("T2D")
    private var rootsLocations: MutableList<LocationEntity> = mutableListOf()

    private var session: Session? = null

    private val childrenInSystemLocationQueryHQL ="select h.location from LocationSystemHierarchyEntity h where h.parent.key = :parentKey and h.system.system = :system order by h.order asc"
    private val parentInSystemLocationQueryHQL ="select h.parent from LocationSystemHierarchyEntity h where h.location.key = :childKey and h.system.system = :system order by h.order asc"

    private fun getSession(): Session? {
        if(session == null) {
            session = sessionFactory.openSession()
        }
        return session
    }

    fun getSystem(system: String): SystemEntity {
        getSession()
        val query: Query<SystemEntity> = session!!.createQuery("from SystemEntity where system = :system", SystemEntity::class.java)
        query.setParameter("system", system)
        return query.singleResult
    }

    fun getSystemList(): List<SystemEntity> {
        getSession()
        val query: Query<SystemEntity> = session!!.createQuery("from SystemEntity", SystemEntity::class.java)
        val systemList: List<SystemEntity> = query.resultList
        return systemList
    }

    fun lookupLocationRoots(system: SystemEntity): MutableList<LocationEntity> {
        getSession()

        if (loaded) {
            return rootsLocations
        } else {
            rootsLocations = mutableListOf()
            val inValue = StringBuilder()
            for (i in 0..<rootLocationIdentifiers.size - 1) {
                inValue.append("'").append(rootLocationIdentifiers[i]).append("',")
            }
            inValue.append("'").append(rootLocationIdentifiers[rootLocationIdentifiers.size - 1]).append("'")
            val query: Query<LocationEntity> = session!!.createQuery("from LocationEntity as location where location.key in ($inValue)", LocationEntity::class.java)
            val rootLocationEntities: List<LocationEntity> = query.list()
            for (loc in rootLocationEntities) {
                rootsLocations.add(loc)
            }
            loaded = true
            return rootsLocations
        }
    }

    fun getChildrenInSystem(parent: LocationEntity, systemString: String): List<LocationEntity> {
        return getChildrenInSystem(parent, getSystem(systemString))
    }

    fun getChildrenInSystem(parent: LocationEntity, system: SystemEntity): List<LocationEntity> {
        getSession()
        val query: Query<LocationEntity> = session!!.createQuery(childrenInSystemLocationQueryHQL, LocationEntity::class.java)

        query.setParameter("parentKey", parent.key)
        query.setParameter("system", system.system)

        val childrenLocations: List<LocationEntity> = query.resultList

        return childrenLocations
    }

    fun getParentInSystem(child: LocationEntity, systemString: String): LocationEntity {
        return getParentInSystem(child, getSystem(systemString))
    }

    fun getParentInSystem(child: LocationEntity, system: SystemEntity): LocationEntity {
        getSession()
        val query: Query<LocationEntity> = session!!.createQuery(parentInSystemLocationQueryHQL, LocationEntity::class.java)

        query.setParameter("childKey", child.key)
        query.setParameter("system", system.system)

        val parentLocation: LocationEntity = query.singleResult

        return parentLocation
    }


}