package com.ventia

import com.ventia.controller.AssetController
import com.ventia.entities.*
import com.ventia.entity.AssetEntity
import com.ventia.entity.AssetStatusEntity
import com.ventia.entity.TypeEntity
import com.ventia.entity.classification.*
import com.ventia.gui.MainFrame
import com.ventia.gui.assetview.AssetHierarchyView
import com.ventia.model.AssetModel
import liquibase.util.LiquibaseUtil
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.cfg.JdbcSettings.*
import org.hibernate.query.Query
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import javax.swing.UIManager


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

    val c: Connection = DriverManager.getConnection("jdbc:hsqldb:file:target/test/db", "SA", "")
    val s: Statement = c.createStatement()
    s.close()
    c.close()

    println(LiquibaseUtil.getBuildVersionInfo())

    val sessionFactory: SessionFactory = Configuration()
        .addAnnotatedClass(UniclassEntityEntity::class.java)
        .addAnnotatedClass(UniclassElementFunctionEntity::class.java)
        .addAnnotatedClass(UniclassSystemEntity::class.java)
        .addAnnotatedClass(UniclassProductEntity::class.java)
        .addAnnotatedClass(UniclassSpaceLocationEntity::class.java)
        .addAnnotatedClass(UniclassComplexEntity::class.java)
        .addAnnotatedClass(ClassificationEntity::class.java)
        .addAnnotatedClass(ClassificationManagedAssetEntity::class.java)
        .addAnnotatedClass(ClassificationComplexEntity::class.java)
        .addAnnotatedClass(ClassificationComplexEntityLocationEntity::class.java)
        .addAnnotatedClass(ClassificationAssetEntity::class.java)


        .addAnnotatedClass(TypeEntity::class.java)
        .addAnnotatedClass(DisciplineEntity::class.java)
        .addAnnotatedClass(SystemEntity::class.java)
        .addAnnotatedClass(LocationStatusEntity::class.java)
        .addAnnotatedClass(LocationEntity::class.java)
        .addAnnotatedClass(LocationSystemHierarchyEntity::class.java)
        .addAnnotatedClass(AssetStatusEntity::class.java)
        .addAnnotatedClass(AssetEntity::class.java)
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

//    sessionFactory.inSession({ session: Session ->
//        val parentLocation = session
//            .createSelectionQuery("from LocationEntity where key = '6203'", LocationEntity::class.java)
//            .singleResult
//        println("Location: $parentLocation")
//        val children = parentLocation.children
//        println("Children Count: ${children.size}")
//
//        val childLocation = children.first()
//
//        println("Location: $childLocation Parents: ${childLocation.parents}")
//
//    })

    sessionFactory.inSession({ session: Session ->
        val hql ="select h.location from LocationSystemHierarchyEntity h where h.parent is null and h.system.system = :system order by h.order asc"

        val query: Query<LocationEntity> = session.createQuery(hql, LocationEntity::class.java)

        query.setParameter("system", "POWER");

        val roots: List<LocationEntity> = query.resultList;

        for (root in roots) {
            println(root)
        }
    })



    val frame: MainFrame = MainFrame()
    frame.title = "Ventia: T2D AMIS Validation Tool"
    frame.size = Dimension(1400, 1000)
    frame.addWindowListener(object : WindowAdapter() {
        override fun windowClosing(e: WindowEvent) {
            sessionFactory.close()
            System.exit(0)
        }
    })

    val assetModel: AssetModel = AssetModel(sessionFactory)
    val assetController: AssetController = AssetController(assetModel)
    val assetHierarchyView = AssetHierarchyView(assetController)

    frame.add(assetHierarchyView, BorderLayout.CENTER)
    frame.isVisible = true
}