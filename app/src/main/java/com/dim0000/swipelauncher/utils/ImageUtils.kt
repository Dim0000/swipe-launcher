package com.dim0000.swipelauncher.utils

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

fun getIconName(icon: ImageVector): String {
    return icon.name.split(".")[1]
}

fun iconByName(name: String): ImageVector {
    val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
    val method = cl.declaredMethods.first()
    return method.invoke(null, Icons.Filled) as ImageVector
}