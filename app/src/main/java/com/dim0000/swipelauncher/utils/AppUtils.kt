package com.dim0000.swipelauncher.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

data class AppData(
    val label: String,
    val icon: Drawable,
    val packageName: String
)

@SuppressLint("QueryPermissionsNeeded")
suspend fun getInstalledApps(context: Context): List<AppData> {
    Timber.d("start")
    val pm = context.packageManager
    val flags =
        PackageManager.MATCH_UNINSTALLED_PACKAGES or PackageManager.MATCH_DISABLED_COMPONENTS
    return withContext(Dispatchers.IO) {
        pm.getInstalledApplications(flags)
            .asSequence()
            .filter {
                (pm.getLaunchIntentForPackage(it.packageName)) != null
            }
            .map {
                AppData(
                    label = it.loadLabel(pm).toString(),
                    icon = it.loadIcon(pm),
                    packageName = it.packageName
                )
            }
            .toList()
            .also { installedApps ->
                Timber.d("AppData=$installedApps")
            }
    }
}

fun launchApp(context: Context, packageName: String) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    }
}