package com.dim0000.swipelauncher

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dim0000.swipelauncher.data.container.AppContainer
import com.dim0000.swipelauncher.logging.TimberSetting
import com.dim0000.swipelauncher.utils.Constants.DATASTORE_NAME
import timber.log.Timber

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATASTORE_NAME
)

class MyApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberSetting(this))
        }
        Timber.d("start")
//        this.applicationContext.deleteDatabase(com.dim0000.swipelauncher.utils.Constants.DATABASE_APP_DATA)
        container = AppContainer(this, dataStore)
    }
}