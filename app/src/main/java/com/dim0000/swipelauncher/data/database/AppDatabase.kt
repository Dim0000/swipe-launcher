package com.dim0000.swipelauncher.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dim0000.swipelauncher.data.dao.MainGridDao
import com.dim0000.swipelauncher.data.dao.SubGridDao
import com.dim0000.swipelauncher.data.entity.MainGridEntity
import com.dim0000.swipelauncher.data.entity.SubGridEntity
import com.dim0000.swipelauncher.utils.Constants.DATABASE_APP_DATA
import timber.log.Timber

@Database(entities = [MainGridEntity::class, SubGridEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainGridDao(): MainGridDao
    abstract fun subGridDao(): SubGridDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            Timber.d("start")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_APP_DATA
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                        Timber.d("Database initialized")
                    }
            }
        }
    }
}