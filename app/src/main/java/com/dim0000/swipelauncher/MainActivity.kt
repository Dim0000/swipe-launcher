package com.dim0000.swipelauncher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.dim0000.swipelauncher.service.OverlayService
import com.dim0000.swipelauncher.ui.screen.Root
import com.dim0000.swipelauncher.ui.theme.SwipeLauncherTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("start")

        requestOverlayPermission()

        val intent = Intent(this, OverlayService::class.java)
        ContextCompat.startForegroundService(this, intent)

        enableEdgeToEdge()
        setContent {
            SwipeLauncherTheme {
                Root()
            }
        }
    }

    private val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (isOverlayGranted()) {
            Timber.d("Overlay permission granted")
        } else {
            Timber.d("Overlay permission denied")
            finish()
        }
    }

    private fun requestOverlayPermission() {
        Timber.d("start")
        if (isOverlayGranted()) return
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        overlayPermissionLauncher.launch(intent)
    }

    private fun isOverlayGranted() = Settings.canDrawOverlays(this)
}