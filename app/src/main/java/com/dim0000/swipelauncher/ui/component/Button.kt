package com.dim0000.swipelauncher.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dim0000.swipelauncher.ui.theme.SwipeLauncherTheme
import com.dim0000.swipelauncher.ui.viewmodel.StateViewModel
import com.dim0000.swipelauncher.utils.Constants.GRID_BACKGROUND_COLOR
import com.dim0000.swipelauncher.utils.Constants.GRID_BUTTON_ICON_SIZE
import com.dim0000.swipelauncher.utils.Constants.GRID_BUTTON_SIZE
import com.dim0000.swipelauncher.utils.Constants.GRID_BUTTON_TEXT_COLOR
import com.dim0000.swipelauncher.utils.Constants.GRID_ROUNDED_CORNER_SHAPE_PERCENT
import com.dim0000.swipelauncher.utils.Constants.MENU_BUTTON_PADDING
import com.dim0000.swipelauncher.utils.Constants.MENU_GRID_ID
import com.dim0000.swipelauncher.utils.Constants.TEXT_SIZE
import com.dim0000.swipelauncher.utils.SwipeDirection

@Composable
fun MainGridButton(
    stateViewModel: StateViewModel,
    mainGridId: Int,
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        label = "",
    )
    CustomButton(
        modifier = modifier
            .scale(scale)
            .swipeGestureDetector(
                onStart = {
                    isPressed = true
                    stateViewModel.setClickedMainGridId(mainGridId)
                    stateViewModel.setIsSubGridButtonClicked(false)
                    stateViewModel.setShowSubGrid(true)
                    stateViewModel.setSwipeDirection(SwipeDirection.NONE)
                },
                onSwipeDirection = { direction ->
                    stateViewModel.setSwipeDirection(direction)
                },
                onEnd = {
                    isPressed = false
                    stateViewModel.setIsSubGridButtonClicked(true)
                    stateViewModel.setShowSubGrid(false)
                }
            ),
        painter = painter,
        text = text,
        textColor = Color(GRID_BUTTON_TEXT_COLOR)
    )
}

@Composable
fun SubGridButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    isSelected: Boolean,
    isClicked: Boolean
) {
    if (isClicked) {
        LaunchedEffect(Unit) {
            onClick()
        }
    }
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        label = "",
    )
    CustomButton(
        onClick = onClick,
        modifier = modifier.scale(scale),
        enabled = false,
        painter = painter,
        text = text,
        textColor = Color(GRID_BUTTON_TEXT_COLOR)
    )
}

@Composable
fun DummyButton() {
    CustomButton(
        modifier = Modifier.alpha(0f),
        painter = rememberVectorPainter(Icons.Default.CropSquare),
        text = ""
    )
}

@Composable
fun CustomButton(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    painter: Painter,
    text: String,
    textColor: Color? = null
) {
    Column(
        modifier = modifier
            .width(GRID_BUTTON_SIZE.dp)
            .clickable(enabled = enabled,
                onClick = {
                    onClick?.invoke()
                }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(GRID_BUTTON_ICON_SIZE.dp),
            colorFilter = if (painter is VectorPainter) ColorFilter.tint(Color.White) else null
        )
        Text(
            text = text,
            modifier = Modifier,
            color = textColor ?: MaterialTheme.colorScheme.onBackground,
            fontSize = TEXT_SIZE.sp,
            textAlign = TextAlign.Center,
            lineHeight = TEXT_SIZE.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun MenuButton(
    stateViewModel: StateViewModel,
    modifier: Modifier,
) {
    MainGridButton(
        stateViewModel = stateViewModel,
        mainGridId = MENU_GRID_ID,
        modifier = modifier
            .background(
                color = Color(GRID_BACKGROUND_COLOR),
                shape = RoundedCornerShape(GRID_ROUNDED_CORNER_SHAPE_PERCENT)
            )
            .padding(MENU_BUTTON_PADDING.dp),
        painter = rememberVectorPainter(Icons.Default.Menu),
        text = "Menu"
    )
}


@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    SwipeLauncherTheme {
        MainGridButton(
            stateViewModel = viewModel(),
            mainGridId = 0,
            modifier = Modifier,
            painter = rememberVectorPainter(Icons.Default.Menu),
            text = "123456789"
        )
    }
}