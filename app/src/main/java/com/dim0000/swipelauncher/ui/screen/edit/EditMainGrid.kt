package com.dim0000.swipelauncher.ui.screen.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dim0000.swipelauncher.ui.component.MainGridButton
import com.dim0000.swipelauncher.ui.viewmodel.DataBaseViewModel
import com.dim0000.swipelauncher.ui.viewmodel.StateViewModel
import com.dim0000.swipelauncher.utils.Constants.GRID_BACKGROUND_COLOR
import com.dim0000.swipelauncher.utils.Constants.GRID_BUTTON_SIZE
import com.dim0000.swipelauncher.utils.Constants.GRID_PADDING
import com.dim0000.swipelauncher.utils.Constants.GRID_ROUNDED_CORNER_SHAPE_PERCENT
import com.dim0000.swipelauncher.utils.Constants.MAIN_GRID_COLUMNS
import com.dim0000.swipelauncher.utils.Constants.MAIN_GRID_COUNT
import com.dim0000.swipelauncher.utils.iconByName
import timber.log.Timber

@Composable
fun EditMainGrid(
    dataBaseViewModel: DataBaseViewModel,
    stateViewModel: StateViewModel
) {
    Timber.v("start")
    val appData by dataBaseViewModel.appData.collectAsStateWithLifecycle()
    val mainGridData = appData.mainGridData

    LazyVerticalGrid(
        columns = GridCells.Fixed(MAIN_GRID_COLUMNS),
        modifier = Modifier
            .background(
                color = Color(GRID_BACKGROUND_COLOR),
                shape = RoundedCornerShape(GRID_ROUNDED_CORNER_SHAPE_PERCENT)
            )
            .padding(GRID_PADDING.dp)
            .width(GRID_BUTTON_SIZE * MAIN_GRID_COLUMNS.dp),
        userScrollEnabled = false
    ) {
        items(MAIN_GRID_COUNT) { index ->
            val mainGridEntity =
                mainGridData.find { it.mainGridId == index }
            if (mainGridEntity != null) {
                MainGridButton(
                    stateViewModel = stateViewModel,
                    mainGridId = index,
                    modifier = Modifier,
                    painter = rememberVectorPainter(iconByName(mainGridEntity.iconName)),
                    text = mainGridEntity.name
                )
            } else {
                MainGridButton(
                    stateViewModel = stateViewModel,
                    mainGridId = index,
                    modifier = Modifier.alpha(0.5f),
                    painter = rememberVectorPainter(Icons.Default.AddCircleOutline),
                    text = "Add"
                )
            }
        }
    }
}