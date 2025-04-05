package com.ventia

import com.ventia.entities.*
import liquibase.util.LiquibaseUtil
import org.hibernate.Session
import org.hibernate.cfg.Configuration
import org.hibernate.cfg.JdbcSettings.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val c: Connection = DriverManager.getConnection("jdbc:hsqldb:file:target/test/db", "SA", "")
    val s: Statement = c.createStatement()
    s.close()
    c.close()

    println(LiquibaseUtil.getBuildVersionInfo())

    val sessionFactory = Configuration()
        .addAnnotatedClass(DisciplineEntity::class.java)
        .addAnnotatedClass(SystemEntity::class.java)
        .addAnnotatedClass(LocationStatusEntity::class.java)
        .addAnnotatedClass(LocationEntity::class.java)
        .addAnnotatedClass(LocationSystemHierarchyEntity::class.java)
        .setProperty(JAKARTA_JDBC_URL, "jdbc:hsqldb:file:target/dev/db")
        .setProperty(JAKARTA_JDBC_USER, "SA")
        .setProperty(JAKARTA_JDBC_PASSWORD, "") // use Agroal connection pool
        //.setProperty("hibernate.agroal.maxSize", 20) // display SQL in console
        //.setProperty(SHOW_SQL, true)
        //.setProperty(FORMAT_SQL, true)
        //.setProperty(HIGHLIGHT_SQL, true)
        //.setProperty(USE_SQL_COMMENTS, true)
        .buildSessionFactory()

    //sessionFactory.getSchemaManager().exportMappedObjects(true);

    sessionFactory.schemaManager.validateMappedObjects()

    //sessionFactory.inTransaction { session ->
    //    session.persist(LocationStatusEntity(id = 0, systemStatus = "TEST", status = "TEST", description = "Test Status", active = true))
    //    session.flush()
    //}

    sessionFactory.inSession({ session: Session ->
        val parentLocation = session
            .createSelectionQuery("from LocationEntity where key = '04500'", LocationEntity::class.java)
            .singleResult
        println("Location: $parentLocation")
        val children = parentLocation.children
        println("Children Count: ${children.size}")

        val childLocation = children.first()

        println("Location: $childLocation Parents: ${childLocation.parents}")

    })

    sessionFactory.close()

}