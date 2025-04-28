package com.ventia.entity.activity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "ACTIVITY_SCHEDULE_DATE_LIST")
class DateListActivityScheduleEntity: ActivityScheduleEntity() {

    @ElementCollection
    @CollectionTable(name="DATE_LIST")
    @Column(name = "LIST_DATE")
    val dates: Set<Date> = mutableSetOf();

}