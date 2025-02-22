package com.dim0000.swipelauncher.ui.screen.drawer

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dim0000.swipelauncher.R
import com.dim0000.swipelauncher.ui.theme.SwipeLauncherTheme
import com.dim0000.swipelauncher.utils.getInstalledApps
import com.dim0000.swipelauncher.utils.AppData
import com.dim0000.swipelauncher.utils.Constants.DRAWER_BUTTON_ICON_SIZE
import com.dim0000.swipelauncher.utils.Constants.DRAWER_BUTTON_SPACER
import com.dim0000.swipelauncher.utils.Constants.DRAWER_COLUMNS
import com.dim0000.swipelauncher.utils.Constants.DRAWER_PADDING
import com.dim0000.swipelauncher.utils.Constants.DRAWER_CONTENT_SPACE
import com.dim0000.swipelauncher.utils.Constants.DRAWER_HORIZONTAL_SPACE
import com.dim0000.swipelauncher.utils.Constants.DRAWER_VERTICAL_SPACE
import com.dim0000.swipelauncher.utils.Constants.GRID_ROUNDED_CORNER_SHAPE_PERCENT
import com.dim0000.swipelauncher.utils.Constants.TEXT_SIZE
import com.dim0000.swipelauncher.utils.launchApp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import timber.log.Timber

@Composable
fun Drawer() {
    Timber.v("start")
    val context = LocalContext.current
    val activity = context as? Activity
    var installedApps by remember { mutableStateOf<List<AppData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        installedApps = getInstalledApps(context)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .padding(DRAWER_PADDING.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(GRID_ROUNDED_CORNER_SHAPE_PERCENT)
            )
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(DRAWER_COLUMNS),
                contentPadding = PaddingValues(DRAWER_CONTENT_SPACE.dp),
                verticalArrangement = Arrangement.spacedBy(DRAWER_VERTICAL_SPACE.dp),
                horizontalArrangement = Arrangement.spacedBy(DRAWER_HORIZONTAL_SPACE.dp)
            ) {
                items(installedApps) { app ->
                    DrawerButton(
                        onClick = {
                            launchApp(context, app.packageName)
                            activity?.finish()
                        },
                        painter = rememberDrawablePainter(drawable = app.icon),
                        text = app.label
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerButton(
    onClick: () -> Unit,
    painter: Painter,
    text: String
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(DRAWER_BUTTON_ICON_SIZE.dp)
        )
        Spacer(modifier = Modifier.height(DRAWER_BUTTON_SPACER.dp))
        Text(
            text = text,
            modifier = Modifier,
            fontSize = TEXT_SIZE.sp,
            textAlign = TextAlign.Center,
            lineHeight = TEXT_SIZE.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            minLines = 2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppIconButtonPreview() {
    val context = LocalContext.current
    SwipeLauncherTheme {
        DrawerButton(
            onClick = { },
            painter = rememberDrawablePainter(drawable = context.getDrawable(R.drawable.ic_launcher_background)!!),
            text = "TEST",
        )
    }
}