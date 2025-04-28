package com.ventia.entity.activity

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "ACTIVITY_SCHEDULE_METER_BASED")
class MeterBasedActivityScheduleEntity: ActivityScheduleEntity() {
}