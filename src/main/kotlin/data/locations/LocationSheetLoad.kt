package com.ventia.data.locations

import com.ventia.entities.*
import com.ventia.entity.AssetEntity
import com.ventia.entity.AssetStatusEntity
import com.ventia.entity.TypeEntity
import com.ventia.entity.classification.*
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.cfg.JdbcSettings.*
import org.hibernate.query.Query
import java.io.File
import java.io.FileInputStream
import java.util.*


fun main() {
    print("Blah")


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
        .setProperty(SHOW_SQL, true)
        .setProperty(FORMAT_SQL, true)
        .setProperty(HIGHLIGHT_SQL, true)
        .setProperty(USE_SQL_COMMENTS, true)
        .buildSessionFactory()

    //sessionFactory.getSchemaManager().exportMappedObjects(true);

    sessionFactory.schemaManager.validateMappedObjects()



    val testFile = File(".")
    println(testFile.absoluteFile)

    val file = FileInputStream("target/classes/sheets/Location Referencing Library.xlsx")
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheetAt(0)

    val headerRow = sheet.getRow(0)
    val lastCell = headerRow.lastCellNum

    for (i in 0 until lastCell) {
        val cell = headerRow.getCell(i)
        println(cell.stringCellValue)
    }

    val rowCount = sheet.physicalNumberOfRows

    val locationMap = mutableMapOf<String, LocationTransferObject>()

    for (i in 1 until rowCount-1) {
        val row = sheet.getRow(i)

        val identifierCell = row.getCell(3)
        if (identifierCell == null) continue
        val identifier = identifierCell.stringCellValue.trim()
        if (identifier.isEmpty()) continue

        val lastCellNum = row.lastCellNum.toInt()

        val locationTransferObject: LocationTransferObject = LocationTransferObject(
            identifier,
            hashTag = row.getCell(1).stringCellValue.trim(),
            key = row.getCell(6).stringCellValue.trim(),
            description = row.getCell(4).stringCellValue?.trim(),
        )

        locationTransferObject.status = "ACTIVE"


        if (lastCellNum>2 && row.getCell(2) != null && row.getCell(2).stringCellValue.trim().isNotEmpty())
            locationTransferObject.parentHashTag = row.getCell(2)?.stringCellValue?.trim()
        if (lastCellNum>5 && row.getCell(5) != null && row.getCell(5).stringCellValue.trim().isNotEmpty())
            locationTransferObject.parentIdentifier = row.getCell(5)?.stringCellValue?.trim()
        if (lastCellNum>18 && row.getCell(18) != null && row.getCell(28) != null && row.getCell(18).stringCellValue.trim().isNotEmpty())
            locationTransferObject.uniclassCo = row.getCell(18)?.stringCellValue?.trim()
        if (lastCellNum>20 && row.getCell(20) != null && row.getCell(20).stringCellValue.trim().isNotEmpty())
            locationTransferObject.uniclassEn = row.getCell(20)?.stringCellValue?.trim()
        if (lastCellNum>22 && row.getCell(22) != null && row.getCell(22).stringCellValue.trim().isNotEmpty())
            locationTransferObject.uniclassSL = row.getCell(22)?.stringCellValue?.trim()

        if (lastCellNum>16) {
            val groupCode = row.getCell(16)?.stringCellValue?.trim()
            if (groupCode?.endsWith("A") == true) {
                locationTransferObject.type = "ROAD"
            } else if (groupCode?.endsWith("B") == true) {
                locationTransferObject.type = "FACILITY"
            } else if (groupCode?.endsWith("C") == true) {
                locationTransferObject.type = "COUNCIL"
            }
            locationTransferObject.groupCode = groupCode
        }

        if (lastCellNum>17 && row.getCell(17) != null) {
            val sortOrder = row.getCell(17)?.numericCellValue
            locationTransferObject.sortOrder = sortOrder!!.toInt()
        }




        locationMap[locationTransferObject.hashTag] = locationTransferObject
    }
    workbook.close()


    val session = sessionFactory.openSession()

    val hql ="from LocationStatusEntity l where l.status = :STATUS"
    val query: Query<LocationStatusEntity> = session.createQuery(hql, LocationStatusEntity::class.java)
    query.setParameter("STATUS", "ACTIVE");
    val activeStatus: LocationStatusEntity = query.singleResult;

    val systemsQuery = session.createSelectionQuery("from SystemEntity where system = :SYSTEM", SystemEntity::class.java)
    systemsQuery.setParameter("SYSTEM", "DEFAULT");
    val system = systemsQuery.singleResult

    val locationQuery = session.createSelectionQuery("from LocationEntity where key = :KEY", LocationEntity::class.java)
    locationQuery.setParameter("KEY", "ROADS");
    val roadLocation = locationQuery.singleResult

    locationQuery.setParameter("KEY", "FACILITIES");
    val facilityLocation = locationQuery.singleResult

    locationQuery.setParameter("KEY", "COUNCILS");
    val councilsLocation = locationQuery.singleResult

    println("Road: ${roadLocation.key}, Facility: ${facilityLocation.key}, Councils: ${councilsLocation.key}")



    val blah = locationMap.values.first()
    val rah = mutableSetOf<LocationTransferObject>()
    rah.add(blah)
    for (locationTransferObject in rah) {

        if (locationTransferObject.parentHashTag != null) {
            val parentHash = locationTransferObject.parentHashTag
            val parentTransferObject = locationMap[parentHash]
            println("Child Loc: ${locationTransferObject.key} with parent ${parentTransferObject?.key} (${locationTransferObject.type})")
        } else {
            println("Root Loc: ${locationTransferObject.key} (${locationTransferObject.type})")
        }


        val tx = session.beginTransaction()
        val locationEntity: LocationEntity = LocationEntity (
            key = locationTransferObject.key,
            description = locationTransferObject.description,
            status = activeStatus,
        )

/*
        if (locationTransferObject.parentIdentifier != null) {
            //tx = session.beginTransaction()
            //locationQuery.setParameter("KEY", locationTransferObject.key);
            //val refreshLocation = locationQuery.singleResult

            val locationSystemHierarchyEntity = LocationSystemHierarchyEntity (
                location = locationEntity,
                parent = roadLocation,
                system = system,
                order = locationTransferObject.sortOrder,
            )
            //

            locationEntity.parentsFromAllSystems = locationEntity.parentsFromAllSystems + locationSystemHierarchyEntity
            roadLocation.childrenFromAllSystems = roadLocation.childrenFromAllSystems + locationSystemHierarchyEntity

            //session.saveOrUpdate(locationSystemHierarchyEntity)
        }
*/
        //roadLocation.description = "rah"
        session.persist(locationEntity)
        //session.merge(roadLocation)

        session.flush()

        tx.commit()

//        if (Objects.isNull(session.find(LocationEntity::class.java, locationEntity.id))) {
//            session.persist(locationEntity)
//        } else {
//            session.merge(locationEntity)
//        }
        //

    }


    //



    //if(session.isDirty)
    //    session.flush()
    session.close()

}