package com.challenge.satellites.data.local.satellite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.challenge.satellites.data.local.satellite.model.SatelliteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SatelliteDao {

    @RawQuery(observedEntities = [SatelliteEntity::class])
    fun getSatellites(query: SimpleSQLiteQuery): Flow<List<SatelliteEntity>>

    @Query("SELECT * FROM Satellite WHERE satelliteId = :satelliteId")
    fun getSatelliteDetails(satelliteId: Int): Flow<SatelliteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatellites(satellite: List<SatelliteEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSatellite(satellite: SatelliteEntity)
}