package com.dim0000.swipelauncher.logging

import android.content.Context
import timber.log.Timber

class TimberSetting(private val context: Context) : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "TimberLog", tag + message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            " [C:%s] [L:%s] [M:%s]:",
            super.createStackElementTag(element),
            element.lineNumber,
            element.methodName
        )
    }

}