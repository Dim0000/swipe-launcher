package com.dim0000.swipelauncher.ui.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropSquare
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dim0000.swipelauncher.MyApplication
import com.dim0000.swipelauncher.data.entity.MainGridEntity
import com.dim0000.swipelauncher.data.entity.SubGridEntity
import com.dim0000.swipelauncher.data.repository.DataStoreRepository
import com.dim0000.swipelauncher.data.repository.MainGridRepository
import com.dim0000.swipelauncher.data.repository.SubGridRepository
import com.dim0000.swipelauncher.utils.decodeFromJsonToAppData
import com.dim0000.swipelauncher.utils.getIconName
import com.dim0000.swipelauncher.utils.isValidJsonAppData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber

@Serializable
data class AppData(
    val mainGridData: List<MainGridEntity> = emptyList(),
    val subGridData: List<SubGridEntity> = emptyList()
)

class DataBaseViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val mainGridRepository: MainGridRepository,
    private val subGridRepository: SubGridRepository
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyApplication)
                DataBaseViewModel(
                    application.container.dataStoreRepository,
                    application.container.mainGridRepository,
                    application.container.subGridRepository
                )
            }
        }
    }

    private val _appData = MutableStateFlow(AppData())
    val appData: StateFlow<AppData> = _appData.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadAndSetAppData(this)
        }
    }

    fun loadAndSetAppData(scope: CoroutineScope) {
        Timber.d("start")
        scope.launch {
            val dataStoreAppData = dataStoreRepository.getDataStoreAppData()
            Timber.d("dataStoreAppData=%s", dataStoreAppData)
            if (isValidJsonAppData(dataStoreAppData)) {
                val appData = decodeFromJsonToAppData(dataStoreAppData)
                appData?.let {
                    _appData.update { currentState ->
                        currentState.copy(
                            mainGridData = appData.mainGridData,
                            subGridData = appData.subGridData
                        )
                    }
                }
            }
            setAppDataBase(this)
        }
    }

    fun setAppDataBase(scope: CoroutineScope) {
        Timber.d("start")
        scope.launch {
            var mainGridData = mainGridRepository.getMainGrid().first()
            var subGridData = subGridRepository.getSubGrid().first()
            if (mainGridData.isEmpty()) {
                val job = initializeDatabase(this)
                job.join()
                mainGridData = mainGridRepository.getMainGrid().first()
                subGridData = subGridRepository.getSubGrid().first()
            }
            val currentAppData = _appData.value
            val newAppData = currentAppData.copy(
                mainGridData = mainGridData,
                subGridData = subGridData
            )
            if (currentAppData != newAppData) {
                _appData.update { newAppData }
            }
            Timber.d("appDataBase=%s", _appData.value)
            dataStoreRepository.saveDataStoreAppData(Json.encodeToString(_appData.value))
        }
    }

    fun getMainGrid(): Flow<List<MainGridEntity>> = mainGridRepository.getMainGrid()
    fun getSubGrid(): Flow<List<SubGridEntity>> = subGridRepository.getSubGrid()

    private fun initializeDatabase(scope: CoroutineScope): Job {
        Timber.w("start")
        return scope.launch {
            for (id in listOf(3, 9)) {
                val mainGridEntity = MainGridEntity(
                    mainGridId = id,
                    name = "Icon",
                    iconName = getIconName(Icons.Default.CropSquare)
                )
                mainGridRepository.upsertMainGrid(mainGridEntity)
            }
        }
    }

}