package com.dim0000.swipelauncher.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "sub_grid", primaryKeys = ["main_grid_id", "sub_grid_id"])
data class SubGridEntity(
    @ColumnInfo(name = "main_grid_id")
    val mainGridId: Int,
    @ColumnInfo(name = "sub_grid_id")
    val subGridId: Int,
    @ColumnInfo(name = "action_id")
    val actionId: Int,
    val name: String,
    @ColumnInfo(name = "icon_name")
    val iconName: String,
    @ColumnInfo(name = "package_name")
    val packageName: String?
)

enum class ActionId(val id: Int) {
    APP_LAUNCH(1),
    DRAWER_LAUNCH(2),
    GRID_EDIT(3)
}