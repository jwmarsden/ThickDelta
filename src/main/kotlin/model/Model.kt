package com.ventia.model

import org.hibernate.SessionFactory

abstract class Model(protected val sessionFactory: SessionFactory) {

}