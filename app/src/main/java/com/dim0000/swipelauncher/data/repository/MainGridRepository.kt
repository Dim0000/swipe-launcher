package com.dim0000.swipelauncher.data.repository

import com.dim0000.swipelauncher.data.dao.MainGridDao
import com.dim0000.swipelauncher.data.entity.MainGridEntity
import kotlinx.coroutines.flow.Flow

class MainGridRepository(private val mainGridDao: MainGridDao) {
    fun getMainGrid(): Flow<List<MainGridEntity>> = mainGridDao.getMainGrid()

    suspend fun upsertMainGrid(mainGridEntity: MainGridEntity) =
        mainGridDao.upsertMainGrid(mainGridEntity)

    suspend fun deleteMainGrid(mainGridId: Int) =
        mainGridDao.deleteMainGrid(mainGridId)
}