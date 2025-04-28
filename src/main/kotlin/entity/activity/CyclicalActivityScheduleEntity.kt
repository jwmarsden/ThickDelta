package com.ventia.entity.activity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "ACTIVITY_SCHEDULE_CYCLICAL")
class CyclicalActivityScheduleEntity: ActivityScheduleEntity() {

    val baseDate: Date = Date()

    val startDate: Date = Date()

    val endDate: Date = Date()

    val frequency: Int = 1

}