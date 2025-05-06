/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.challenge.satellites.data.local

import androidx.sqlite.db.SimpleSQLiteQuery
import com.challenge.satellites.data.domain.model.Satellite
import com.challenge.satellites.data.domain.model.toDatabaseEntity
import com.challenge.satellites.data.local.satellite.SatelliteDao
import com.challenge.satellites.data.local.satellite.model.toDomain
import com.challenge.satellites.data.domain.model.Sort
import com.challenge.satellites.data.domain.model.SortDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val satelliteDao: SatelliteDao
) : LocalDataSource {


    override fun getSatellites(
        searchText: String,
        sort: Sort,
        sortDirection: SortDirection
    ): Flow<List<Satellite>> {
        val query = buildQuery(searchText, sort, sortDirection)
        return satelliteDao.getSatellites(query).map { list -> list.map { it.toDomain() } }
    }

    override fun getSatelliteById(id: Int): Flow<Satellite> {
        return satelliteDao.getSatelliteDetails(id).map { it.toDomain() }
    }

    override suspend fun setSatellites(satellites: List<Satellite>) {
        satelliteDao.insertSatellites(satellites.map { it.toDatabaseEntity() })
    }

    override suspend fun setSatellite(satellite: Satellite) {
        satelliteDao.insertSatellite(satellite.toDatabaseEntity())
    }

    private fun buildQuery(
        searchText: String,
        sort: Sort,
        sortDirection: SortDirection
    ): SimpleSQLiteQuery {
        val sortName = if (sort == Sort.ID) "satelliteId" else sort.name
        val queryString = StringBuilder("SELECT * FROM Satellite WHERE ")
        queryString.append("name LIKE '%' || ? || '%' OR ")
        queryString.append("line1 LIKE '%' || ? || '%' OR ")
        queryString.append("line2 LIKE '%' || ? || '%' OR ")
        queryString.append("date LIKE '%' || ? || '%' ")
        queryString.append("ORDER BY $sortName ${sortDirection.name}")

        val args = arrayOf(searchText, searchText, searchText, searchText)
        return SimpleSQLiteQuery(queryString.toString(), args)
    }
}