package com.dim0000.swipelauncher.ui.screen.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dim0000.swipelauncher.ui.component.CustomAnimatedVisibility
import com.dim0000.swipelauncher.ui.component.DummyButton
import com.dim0000.swipelauncher.ui.component.SubGridButton
import com.dim0000.swipelauncher.ui.viewmodel.DataBaseViewModel
import com.dim0000.swipelauncher.ui.viewmodel.StateViewModel
import com.dim0000.swipelauncher.utils.Constants.GRID_BACKGROUND_COLOR
import com.dim0000.swipelauncher.utils.Constants.GRID_BUTTON_SIZE
import com.dim0000.swipelauncher.utils.Constants.GRID_PADDING
import com.dim0000.swipelauncher.utils.Constants.GRID_ROUNDED_CORNER_SHAPE_PERCENT
import com.dim0000.swipelauncher.utils.Constants.MENU_GRID_ID
import com.dim0000.swipelauncher.utils.Constants.SUB_GRID_COLUMNS
import com.dim0000.swipelauncher.utils.Constants.SUB_GRID_COUNT
import com.dim0000.swipelauncher.utils.SwipeDirection
import com.dim0000.swipelauncher.utils.iconByName
import timber.log.Timber

@Composable
fun EditSubGrid(
    dataBaseViewModel: DataBaseViewModel,
    stateViewModel: StateViewModel
) {
    Timber.v("start")
    val appData by dataBaseViewModel.appData.collectAsStateWithLifecycle()
    val uiState by stateViewModel.uiState.collectAsStateWithLifecycle()
    val clickedMainGridId = uiState.clickedMainGridId
    val mainGridData = appData.mainGridData
    val mainGridEntity = mainGridData.find {
        it.mainGridId == clickedMainGridId
    }
    val subGridData = appData.subGridData

    CustomAnimatedVisibility(
        visible = uiState.showSubGrid,
        modifier = Modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(SUB_GRID_COLUMNS),
            modifier = Modifier
                .background(
                    color = Color(GRID_BACKGROUND_COLOR),
                    shape = RoundedCornerShape(GRID_ROUNDED_CORNER_SHAPE_PERCENT)
                )
                .padding(GRID_PADDING.dp)
                .size(GRID_BUTTON_SIZE * SUB_GRID_COLUMNS.dp),
            userScrollEnabled = false
        ) {
            items(SUB_GRID_COUNT) { index ->
                val swipeDirection = uiState.swipeDirection.value
                val isSelected = swipeDirection == index
                val isSubGridButtonClicked = uiState.isSubGridButtonClicked
                val isClicked = isSelected && isSubGridButtonClicked
                val subGridEntity = subGridData.find {
                    it.mainGridId == clickedMainGridId && it.subGridId == index
                }
                if (index == SwipeDirection.NONE.value) {
                    if (clickedMainGridId == MENU_GRID_ID) {
                        SubGridButton(
                            onClick = {},
                            modifier = Modifier,
                            painter = rememberVectorPainter(Icons.Default.Menu),
                            text = "Menu",
                            isSelected = isSelected,
                            isClicked = isClicked
                        )
                    } else if (mainGridEntity != null) {
                        SubGridButton(
                            onClick = {},
                            modifier = Modifier,
                            painter = rememberVectorPainter(iconByName(mainGridEntity.iconName)),
                            text = mainGridEntity.name,
                            isSelected = isSelected,
                            isClicked = isClicked
                        )
                    }
                } else {
                    if (clickedMainGridId == MENU_GRID_ID) {
//                        val buttonConfig = when (index) {
//                            SwipeDirection.LEFT.value -> Triple(
//                                { toDrawer() },
//                                Icons.Default.Android,
//                                "Drawer"
//                            )
//                            SwipeDirection.DOWN.value -> Triple(
//                                { toEdit() },
//                                Icons.Default.Draw,
//                                "Edit"
//                            )
//                            else -> null
//                        }
//                        buttonConfig?.let { (onClick, icon, text) ->
//                            SubGridButton(
//                                onClick = onClick,
//                                modifier = Modifier,
//                                painter = rememberVectorPainter(icon),
//                                text = text,
//                                isSelected = isSelected,
//                                isClicked = isClicked
//                            )
//                        } ?: DummyButton()
//                    } else if (subGridEntity != null) {
//                        SubGridButton(
//                            onClick = { TODO() },
//                            modifier = Modifier,
//                            painter = rememberVectorPainter(iconByName(subGridEntity.iconName)),
//                            text = subGridEntity.name,
//                            isSelected = isSelected,
//                            isClicked = isClicked
//                        )
                    } else {
                        DummyButton()
                    }
                }
            }
        }
    }
}