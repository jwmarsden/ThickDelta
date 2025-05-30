package com.ventia.data

import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

fun main() {
    print("Blah")


    val file = FileInputStream("C:\\Users\\MARSDENJO\\OneDrive - Ventia\\Documents\\T2D Alliance\\Location Referencing Library.xlsx")
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheetAt(0)

    val headerRow = sheet.getRow(0)
    val lastCell = headerRow.lastCellNum

    for (i in 0 until lastCell) {
        val cell = headerRow.getCell(i)
        println(cell.stringCellValue)
    }

    for (row in sheet) {
        for (cell in row) {
            // Process cell data
            //println(cell.toString())
        }
    }
    workbook.close()

}