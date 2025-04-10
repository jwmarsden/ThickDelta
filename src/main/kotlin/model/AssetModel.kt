package com.ventia.model

import com.ventia.entities.LocationEntity
import com.ventia.entities.SystemEntity
import com.ventia.entity.AssetEntity
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.query.Query

class AssetModel(sessionFactory: SessionFactory) : Model(sessionFactory) {

    private var loaded: Boolean = false
    private var rootsLocations: List<LocationEntity> = mutableListOf()

    private var session: Session? = null

    private val locationInSystem ="select h.location from LocationSystemHierarchyEntity h where h.location.key = :locationKey and h.system.system = :system order by h.order asc"
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

    fun lookupLocationRoots(system: SystemEntity): List<LocationEntity> {
        getSession()

        if (loaded) {
            return rootsLocations
        } else {
            val hql ="select h.location from LocationSystemHierarchyEntity h where h.parent is null and h.system.system = :system order by h.order asc"
            val query: Query<LocationEntity> = session!!.createQuery(hql, LocationEntity::class.java)
            query.setParameter("system", system.system);
            rootsLocations = query.resultList;
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

    fun getParentInSystem(child: LocationEntity, systemString: String): LocationEntity? {
        return getParentInSystem(child, getSystem(systemString))
    }

    fun getParentInSystem(child: LocationEntity, system: SystemEntity): LocationEntity? {
        getSession()
        val query: Query<LocationEntity> = session!!.createQuery(parentInSystemLocationQueryHQL, LocationEntity::class.java)

        query.setParameter("childKey", child.key)
        query.setParameter("system", system.system)

        val queryResult: MutableList<LocationEntity> = query.resultList
        return if (queryResult.isEmpty()) {
            null
        } else {
            queryResult.first()
        }
    }

//    fun getParentInSystem(child: AssetEntity, systemString: String): LocationEntity? {
//        return model.getParentInSystem(child, getSystem(systemString))
//    }

    fun getParentInSystem(child: AssetEntity, system: SystemEntity): LocationEntity? {
        getSession()
        val query: Query<LocationEntity> = session!!.createQuery(locationInSystem, LocationEntity::class.java)

        query.setParameter("locationKey", child.parent)
        query.setParameter("system", system.system)

        val queryResult: MutableList<LocationEntity> = query.resultList
        return if (queryResult.isEmpty()) {
            null
        } else {
            queryResult.first()
        }
    }

}