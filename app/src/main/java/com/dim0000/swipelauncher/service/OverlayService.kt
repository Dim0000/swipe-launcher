package com.dim0000.swipelauncher.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.dim0000.swipelauncher.R
import com.dim0000.swipelauncher.ui.screen.OverlayView
import com.dim0000.swipelauncher.ui.theme.SwipeLauncherTheme
import timber.log.Timber

class OverlayService : LifecycleService(), SavedStateRegistryOwner {
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    private val params by lazy {
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
        }
    }

    private val windowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }

    private val overlayView by lazy {
        ComposeView(this).apply {
            setContent {
                SwipeLauncherTheme {
                    OverlayView()
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("start")
        savedStateRegistryController.performRestore(null)
        overlayView.setViewTreeLifecycleOwner(this)
        overlayView.setViewTreeSavedStateRegistryOwner(this)
        windowManager.addView(overlayView, params)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Timber.d("start")
        startForeground(1, createNotification())
        return START_STICKY
    }

    override fun onDestroy() {
        Timber.e("start")
        windowManager.removeView(overlayView)
        super.onDestroy()
    }

    private fun createNotification(): Notification {
        val channelId = "overlay_service_channel"
        val channel = NotificationChannel(
            channelId,
            "Overlay Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Overlay Service")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }
}