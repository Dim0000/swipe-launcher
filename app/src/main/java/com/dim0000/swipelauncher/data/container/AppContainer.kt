package com.dim0000.swipelauncher.data.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dim0000.swipelauncher.data.database.AppDatabase
import com.dim0000.swipelauncher.data.repository.DataStoreRepository
import com.dim0000.swipelauncher.data.repository.MainGridRepository
import com.dim0000.swipelauncher.data.repository.SubGridRepository

class AppContainer(
    private val context: Context,
    private val dataStore: DataStore<Preferences>
) {
    val dataStoreRepository: DataStoreRepository by lazy {
        DataStoreRepository(dataStore)
    }
    val mainGridRepository: MainGridRepository by lazy {
        MainGridRepository(AppDatabase.getDatabase(context).mainGridDao())
    }
    val subGridRepository: SubGridRepository by lazy {
        SubGridRepository(AppDatabase.getDatabase(context).subGridDao())
    }
}