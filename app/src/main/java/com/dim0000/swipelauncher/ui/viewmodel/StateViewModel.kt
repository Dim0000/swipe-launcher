package com.dim0000.swipelauncher.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dim0000.swipelauncher.utils.Constants.MENU_GRID_ID
import com.dim0000.swipelauncher.utils.SwipeDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

data class UiState(
    val showSubGrid: Boolean = false,
    val editGrid: Boolean = false,
    val clickedMainGridId: Int = MENU_GRID_ID,
    val swipeDirection: SwipeDirection = SwipeDirection.NONE,
    val isSubGridButtonClicked : Boolean = false,
    val isEditGridOpenClicked : Boolean = false
)

class StateViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            uiState.collect { state ->
                Timber.v("UiState=$state")
            }
        }
    }

    fun setShowSubGrid(enabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(showSubGrid = enabled.takeIf { it != currentState.showSubGrid }
                ?: currentState.showSubGrid)
        }
    }

    fun setClickedMainGridId(mainGridId: Int) {
        _uiState.update { currentState ->
            currentState.copy(clickedMainGridId = mainGridId.takeIf { it != currentState.clickedMainGridId }
                ?: currentState.clickedMainGridId)
        }
    }

    fun setSwipeDirection(swipeDirection: SwipeDirection) {
        _uiState.update { currentState ->
            currentState.copy(swipeDirection = swipeDirection.takeIf { it != currentState.swipeDirection }
                ?: currentState.swipeDirection)
        }
    }

    fun setIsSubGridButtonClicked(enabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isSubGridButtonClicked = enabled.takeIf { it != currentState.isSubGridButtonClicked }
                ?: currentState.isSubGridButtonClicked)
        }
    }

    fun setIsEditGridOpenClicked(enabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isEditGridOpenClicked = enabled.takeIf { it != currentState.isEditGridOpenClicked }
                ?: currentState.isEditGridOpenClicked)
        }
    }
}