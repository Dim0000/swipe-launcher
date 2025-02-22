package com.dim0000.swipelauncher.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dim0000.swipelauncher.data.entity.MainGridEntity
import com.dim0000.swipelauncher.ui.component.DummyButton
import com.dim0000.swipelauncher.ui.component.MainGridButton
import com.dim0000.swipelauncher.ui.viewmodel.DataBaseViewModel
import com.dim0000.swipelauncher.ui.viewmodel.StateViewModel
import com.dim0000.swipelauncher.utils.Constants.GRID_BACKGROUND_COLOR
import com.dim0000.swipelauncher.utils.Constants.GRID_BUTTON_SIZE
import com.dim0000.swipelauncher.utils.Constants.GRID_PADDING
import com.dim0000.swipelauncher.utils.Constants.GRID_ROUNDED_CORNER_SHAPE_PERCENT
import com.dim0000.swipelauncher.utils.iconByName
import timber.log.Timber

@Composable
fun HomeMainGrid(
    dataBaseViewModel: DataBaseViewModel,
    stateViewModel: StateViewModel
) {
    Timber.v("start")
    val appData by dataBaseViewModel.appData.collectAsStateWithLifecycle()
    val mainGridData = appData.mainGridData
    val duplicateNumbers = getMaxRangeAndResultArray(mainGridData)
    val maxRange = getMaxRange(duplicateNumbers)
    val resultArray = getGridRangeFromDuplicates(duplicateNumbers)

    LazyVerticalGrid(
        columns = GridCells.Fixed(maxRange),
        modifier = Modifier
            .background(
                color = Color(GRID_BACKGROUND_COLOR),
                shape = RoundedCornerShape(GRID_ROUNDED_CORNER_SHAPE_PERCENT)
            )
            .padding(GRID_PADDING.dp)
            .width(GRID_BUTTON_SIZE * maxRange.dp),
        userScrollEnabled = false
    ) {
        items(resultArray) { index ->
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
                DummyButton()
            }
        }
    }
}

data class GridLine(val values: List<Int>, var isActive: Boolean)

val row1 = GridLine(values = intArrayOf(0, 1, 2, 3).toList(), isActive = true)
val row2 = GridLine(values = intArrayOf(4, 5, 6, 7).toList(), isActive = true)
val row3 = GridLine(values = intArrayOf(8, 9, 10, 11).toList(), isActive = true)
val row4 = GridLine(values = intArrayOf(12, 13, 14, 15).toList(), isActive = true)
val col1 = GridLine(values = intArrayOf(0, 4, 8, 12).toList(), isActive = true)
val col2 = GridLine(values = intArrayOf(1, 5, 9, 13).toList(), isActive = true)
val col3 = GridLine(values = intArrayOf(2, 6, 10, 14).toList(), isActive = true)
val col4 = GridLine(values = intArrayOf(3, 7, 11, 15).toList(), isActive = true)
val allGridLines = listOf(row1, row2, row3, row4, col1, col2, col3, col4)
val rowGridLines = listOf(row1, row2, row3, row4)

fun getMaxRangeAndResultArray(mainGridData: List<MainGridEntity>): Set<Int> {
    Timber.d("start")
    allGridLines.forEach { updateGridLineActivity(mainGridData, it) }
    val activeValues = allGridLines.filter { it.isActive }
        .flatMap { it.values }
    val duplicateNumbers = activeValues.groupBy { it }
        .filter { it.value.size > 1 }
        .keys
    Timber.d("duplicateNumbers=$duplicateNumbers")
    return duplicateNumbers
}

fun updateGridLineActivity(mainGridData: List<MainGridEntity>, gridLine: GridLine) {
    val allNull = gridLine.values.all { value ->
        mainGridData.find { it.mainGridId == value } == null
    }
    gridLine.isActive = !allNull
}

fun getMaxRange(duplicateNumbers: Set<Int>): Int {
    var minIndex = Int.MAX_VALUE
    var maxIndex = Int.MIN_VALUE
    rowGridLines.forEach { gridLine ->
        gridLine.values.forEachIndexed { index, value ->
            if (value in duplicateNumbers) {
                minIndex = minOf(minIndex, index)
                maxIndex = maxOf(maxIndex, index)
            }
        }
    }
    return maxIndex - minIndex + 1
}

fun getGridRangeFromDuplicates(duplicateNumbers: Set<Int>): List<Int> {
    val gridSize = 4
    val minValue = duplicateNumbers.minOrNull() ?: return emptyList()
    val maxValue = duplicateNumbers.maxOrNull() ?: return emptyList()
    val result = mutableListOf<Int>()
    val minRow = minValue / gridSize
    val minCol = minValue % gridSize
    val maxRow = maxValue / gridSize
    val maxCol = maxValue % gridSize
    for (row in minRow..maxRow) {
        for (col in minCol..maxCol) {
            result.add(row * gridSize + col)
        }
    }
    return result
}