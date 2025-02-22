package com.dim0000.swipelauncher.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dim0000.swipelauncher.data.entity.SubGridEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubGridDao {
    @Query("SELECT * FROM sub_grid")
    fun getSubGrid(): Flow<List<SubGridEntity>>

    @Upsert
    suspend fun upsertSubGrid(subGridEntity: SubGridEntity)

    @Query(value = """DELETE FROM sub_grid WHERE main_grid_id = (:mainGridId) AND sub_grid_id = (:subGridId)""")
    suspend fun deleteSubGrid(mainGridId: Int, subGridId: Int)
}