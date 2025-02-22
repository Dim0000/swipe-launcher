package com.dim0000.swipelauncher.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dim0000.swipelauncher.ui.component.MenuButton
import com.dim0000.swipelauncher.ui.viewmodel.DataBaseViewModel
import com.dim0000.swipelauncher.ui.viewmodel.StateViewModel
import com.dim0000.swipelauncher.utils.Constants.MAIN_SCREEN_OFFSET_Y
import com.dim0000.swipelauncher.utils.Constants.MAIN_SCREEN_SPACE
import timber.log.Timber

@Composable
fun Home(
    dataBaseViewModel: DataBaseViewModel,

    stateViewModel: StateViewModel,
    toEdit: () -> Unit,
    toDrawer: () -> Unit
) {
    Timber.v("start")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MAIN_SCREEN_SPACE.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(MAIN_SCREEN_OFFSET_Y.dp))
        HomeMainGrid(
            dataBaseViewModel = dataBaseViewModel,
            stateViewModel = stateViewModel
        )
        MenuButton(stateViewModel = stateViewModel)
        HomeSubGrid(
            dataBaseViewModel = dataBaseViewModel,
            stateViewModel = stateViewModel,
            toEdit = toEdit,
            toDrawer = toDrawer
        )
    }
}