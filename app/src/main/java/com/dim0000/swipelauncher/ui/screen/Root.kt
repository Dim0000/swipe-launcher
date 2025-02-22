package com.dim0000.swipelauncher.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dim0000.swipelauncher.ui.screen.drawer.Drawer
import com.dim0000.swipelauncher.ui.screen.edit.Edit
import com.dim0000.swipelauncher.ui.screen.home.Home
import com.dim0000.swipelauncher.ui.viewmodel.DataBaseViewModel
import com.dim0000.swipelauncher.ui.viewmodel.StateViewModel
import com.dim0000.swipelauncher.utils.Constants.MAIN_SCREEN_BACKGROUND
import timber.log.Timber

@Composable
fun Root(
    dataBaseViewModel: DataBaseViewModel = viewModel(factory = DataBaseViewModel.Factory),
    stateViewModel: StateViewModel = viewModel()
) {
    Timber.v("start")
    val activity = LocalContext.current as? ComponentActivity
    val appData by dataBaseViewModel.appData.collectAsStateWithLifecycle()
    val mainGridData = appData.mainGridData
    val subGridData = appData.subGridData
    val navController = rememberNavController()

    LaunchedEffect(mainGridData, subGridData) {
        if (mainGridData.isEmpty()) {
            dataBaseViewModel.loadAndSetAppData(this)
        }
    }

    if (mainGridData.isNotEmpty()) {
        NavHost(
            navController = navController,
            startDestination = Screens.HOME.name,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(MAIN_SCREEN_BACKGROUND))
                .clickable {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
        ) {
            composable(Screens.HOME.name) {
                Home(
                    dataBaseViewModel = dataBaseViewModel,
                    stateViewModel = stateViewModel,
                    toEdit = { navController.navigate(Screens.EDIT.name) },
                    toDrawer = { navController.navigate(Screens.DRAWER.name) }
                )
            }
            composable(Screens.EDIT.name) {
                Edit(
                    dataBaseViewModel = dataBaseViewModel,
                    stateViewModel = stateViewModel
                )
            }
            composable(Screens.DRAWER.name) {
                Drawer()
            }
        }
    }
}

enum class Screens {
    HOME,
    EDIT,
    DRAWER
}