package com.dim0000.swipelauncher.utils

import androidx.compose.ui.geometry.Offset

enum class SwipeDirection(val value: Int) {
    UP_LEFT(0),
    UP(1),
    UP_RIGHT(2),
    LEFT(3),
    NONE(4),
    RIGHT(5),
    DOWN_LEFT(6),
    DOWN(7),
    DOWN_RIGHT(8)
}

const val SWIPE_THRESHOLD = 100f

fun handleSwipeDirection(startPos: Offset, endPos: Offset): SwipeDirection {
    val deltaX = endPos.x - startPos.x
    val deltaY = endPos.y - startPos.y
    return when {
        deltaX < -SWIPE_THRESHOLD && deltaY < -SWIPE_THRESHOLD -> SwipeDirection.UP_LEFT
        deltaX > SWIPE_THRESHOLD && deltaY < -SWIPE_THRESHOLD -> SwipeDirection.UP_RIGHT
        deltaX < -SWIPE_THRESHOLD && deltaY > SWIPE_THRESHOLD -> SwipeDirection.DOWN_LEFT
        deltaX > SWIPE_THRESHOLD && deltaY > SWIPE_THRESHOLD -> SwipeDirection.DOWN_RIGHT
        deltaY < -SWIPE_THRESHOLD -> SwipeDirection.UP
        deltaY > SWIPE_THRESHOLD -> SwipeDirection.DOWN
        deltaX < -SWIPE_THRESHOLD -> SwipeDirection.LEFT
        deltaX > SWIPE_THRESHOLD -> SwipeDirection.RIGHT
        else -> SwipeDirection.NONE
    }
}