package com.dim0000.swipelauncher.data.repository

import com.dim0000.swipelauncher.data.dao.SubGridDao
import com.dim0000.swipelauncher.data.entity.SubGridEntity
import kotlinx.coroutines.flow.Flow

class SubGridRepository(private val subGridDao: SubGridDao) {
    fun getSubGrid(): Flow<List<SubGridEntity>> = subGridDao.getSubGrid()

    suspend fun upsertSubGrid(subGridEntity: SubGridEntity) =
        subGridDao.upsertSubGrid(subGridEntity)

    suspend fun deleteSubGrid(mainGridId: Int, subGridId: Int) =
        subGridDao.deleteSubGrid(mainGridId, subGridId)
}