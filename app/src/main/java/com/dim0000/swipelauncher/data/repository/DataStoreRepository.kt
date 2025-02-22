package com.dim0000.swipelauncher.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dim0000.swipelauncher.utils.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val DATASTORE_APP_DATA = stringPreferencesKey(Constants.DATASTORE_APP_DATA)
    }

    suspend fun getDataStoreAppData(): String {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    Timber.e("DataStore exception", exception)
                    throw exception
                }
            }.map { preferences ->
                preferences[DATASTORE_APP_DATA] ?: Constants.DATASTORE_UNKNOWN
            }
            .first()
    }

    suspend fun saveDataStoreAppData(dataStoreAppData: String) {
        dataStore.edit { preferences ->
            preferences[DATASTORE_APP_DATA] = dataStoreAppData
        }
    }
}