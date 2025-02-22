package com.dim0000.swipelauncher.ui.component

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.dim0000.swipelauncher.utils.SwipeDirection
import com.dim0000.swipelauncher.utils.handleSwipeDirection

fun Modifier.swipeGestureDetector(
    onStart: () -> Unit = {},
    onSwipeDirection: (SwipeDirection) -> Unit,
    onEnd: () -> Unit = {},
): Modifier {
    return this.pointerInput(Unit) {
        awaitEachGesture {
            awaitFirstDown(false)
            onStart()
            var startPos = Offset(0f, 0f)
            do {
                val event = awaitPointerEvent()
                event.changes.forEach { change ->
                    if (change.pressed) {
                        if (startPos == Offset(0f, 0f)) {
                            startPos = change.position
                        }
                        onSwipeDirection(handleSwipeDirection(startPos, change.position))
                    }
                }
            } while (event.changes.any { it.pressed })
            onEnd()
        }
    }
}