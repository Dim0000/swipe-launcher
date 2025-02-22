package com.dim0000.swipelauncher.utils

import com.dim0000.swipelauncher.ui.viewmodel.AppData
import com.dim0000.swipelauncher.utils.Constants.DATASTORE_UNKNOWN
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import timber.log.Timber

fun isValidJsonAppData(jsonAppData: String): Boolean {
    if (jsonAppData == DATASTORE_UNKNOWN) {
        return false
    }
    return !Json.parseToJsonElement(jsonAppData).jsonObject.isEmpty()
}

fun decodeFromJsonToAppData(jsonAppData: String): AppData? {
    return try {
        Json.decodeFromString<AppData>(jsonAppData)
    } catch (e: SerializationException) {
        Timber.e("SerializationException: %s", e.message)
        null
    }
}
