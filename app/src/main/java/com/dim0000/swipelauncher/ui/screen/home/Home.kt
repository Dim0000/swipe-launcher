package com.dim0000.swipelauncher.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dim0000.swipelauncher.ui.component.MenuButton
import com.dim0000.swipelauncher.ui.viewmodel.DataBaseViewModel
import com.dim0000.swipelauncher.ui.viewmodel.StateViewModel
import timber.log.Timber

@Composable
fun Home(
    dataBaseViewModel: DataBaseViewModel,

    stateViewModel: StateViewModel,
    toEdit: () -> Unit,
    toDrawer: () -> Unit
) {
    Timber.v("start")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            HomeMainGrid(
                dataBaseViewModel = dataBaseViewModel,
                stateViewModel = stateViewModel,
                modifier = Modifier
            )
        }
        MenuButton(
            stateViewModel = stateViewModel,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            HomeSubGrid(
                dataBaseViewModel = dataBaseViewModel,
                stateViewModel = stateViewModel,
                toEdit = toEdit,
                toDrawer = toDrawer,
                modifier = Modifier
            )
        }
    }
}