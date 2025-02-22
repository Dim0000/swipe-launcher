package com.dim0000.swipelauncher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "setting")
data class SettingEntity(
    @PrimaryKey
    val key: String,
    val value: String
)