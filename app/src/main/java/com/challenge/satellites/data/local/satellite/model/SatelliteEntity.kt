package com.challenge.satellites.data.local.satellite.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.challenge.satellites.data.domain.model.Satellite

@Entity(tableName = "Satellite")
data class SatelliteEntity(
    @PrimaryKey
    val satelliteId: Int,
    val name: String,
    val line1: String,
    val line2: String,
    val date: String
)

fun SatelliteEntity.toDomain(): Satellite {
    return Satellite(
        satelliteId = satelliteId,
        name = name,
        line1 = line1,
        line2 = line2,
        date = date,
    )
}