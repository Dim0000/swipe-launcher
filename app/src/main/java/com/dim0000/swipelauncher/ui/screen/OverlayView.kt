package com.dim0000.swipelauncher.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.dim0000.swipelauncher.ui.component.swipeGestureDetector
import com.dim0000.swipelauncher.utils.Constants.LEFT_OVERLAY_VIEW_BACKGROUND
import com.dim0000.swipelauncher.utils.Constants.LEFT_OVERLAY_VIEW_HEIGHT
import com.dim0000.swipelauncher.utils.Constants.LEFT_OVERLAY_VIEW_OFFSET_Y
import com.dim0000.swipelauncher.utils.Constants.LEFT_OVERLAY_VIEW_WIDTH
import com.dim0000.swipelauncher.utils.Constants.RIGHT_OVERLAY_VIEW_BACKGROUND
import com.dim0000.swipelauncher.utils.Constants.RIGHT_OVERLAY_VIEW_HEIGHT
import com.dim0000.swipelauncher.utils.Constants.RIGHT_OVERLAY_VIEW_OFFSET_Y
import com.dim0000.swipelauncher.utils.Constants.RIGHT_OVERLAY_VIEW_WIDTH
import com.dim0000.swipelauncher.utils.SwipeDirection
import timber.log.Timber

@Composable
fun OverlayView() {
    Timber.v("start")
    val swipeDirection by remember { mutableStateOf(SwipeDirection.NONE) }
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density
    val screenWidth = context.resources.displayMetrics.widthPixels
    val boxWidth = RIGHT_OVERLAY_VIEW_WIDTH * density
    val offsetX = (screenWidth - boxWidth).toInt()
    val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    if (launchIntent != null) {
        launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    Popup(
        offset = IntOffset(x = 0, y = (LEFT_OVERLAY_VIEW_OFFSET_Y * density).toInt())
    ) {
        Box(
            modifier = Modifier
                .width(LEFT_OVERLAY_VIEW_WIDTH.dp)
                .height(LEFT_OVERLAY_VIEW_HEIGHT.dp)
                .background(Color(LEFT_OVERLAY_VIEW_BACKGROUND))
                .commonOverlayActions(
                    swipeDirection = remember { mutableStateOf(swipeDirection) },
                    context = context,
                    launchIntent = launchIntent
                )
        )
    }
    Popup(
        offset = IntOffset(x = offsetX, y = (RIGHT_OVERLAY_VIEW_OFFSET_Y * density).toInt())
    ) {
        Box(
            modifier = Modifier
                .width(RIGHT_OVERLAY_VIEW_WIDTH.dp)
                .height(RIGHT_OVERLAY_VIEW_HEIGHT.dp)
                .background(Color(RIGHT_OVERLAY_VIEW_BACKGROUND))
                .commonOverlayActions(
                    swipeDirection = remember { mutableStateOf(swipeDirection) },
                    context = context,
                    launchIntent = launchIntent
                )
        )
    }
}

fun Modifier.commonOverlayActions(
    swipeDirection: MutableState<SwipeDirection>,
    context: Context,
    launchIntent: Intent?
): Modifier = this
    .clickable(onClick = { })
    .swipeGestureDetector(
        onStart = {
            swipeDirection.value = SwipeDirection.NONE
        },
        onSwipeDirection = { direction ->
            swipeDirection.value = direction
        },
        onEnd = {
            if (swipeDirection.value != SwipeDirection.NONE) {
                launchIntent?.let { context.startActivity(it) }
            }
        }
    )