package com.challenge.satellites.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.satellites.data.local.satellite.SatelliteDao
import com.challenge.satellites.data.local.satellite.model.SatelliteEntity

@Database(entities = [SatelliteEntity::class], version = 1, exportSchema = false)
abstract class SatelliteDatabase : RoomDatabase() {
     abstract fun satelliteDao(): SatelliteDao
}