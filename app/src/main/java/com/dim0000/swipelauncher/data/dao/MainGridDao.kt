package com.dim0000.swipelauncher.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dim0000.swipelauncher.data.entity.MainGridEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainGridDao {
    @Query("SELECT * FROM main_grid")
    fun getMainGrid(): Flow<List<MainGridEntity>>

    @Upsert
    suspend fun upsertMainGrid(mainGridEntity: MainGridEntity)

    @Query(value = """DELETE FROM main_grid WHERE main_grid_id in (:mainGridId)""")
    suspend fun deleteMainGrid(mainGridId: Int)
}