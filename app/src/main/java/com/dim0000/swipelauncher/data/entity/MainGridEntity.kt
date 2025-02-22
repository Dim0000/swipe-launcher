package com.dim0000.swipelauncher.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "main_grid")
data class MainGridEntity(
    @PrimaryKey
    @ColumnInfo(name = "main_grid_id")
    val mainGridId: Int,
    val name: String,
    @ColumnInfo(name = "icon_name")
    val iconName: String
)
