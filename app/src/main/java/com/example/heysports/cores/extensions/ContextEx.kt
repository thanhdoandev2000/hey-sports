package com.example.heysports.cores.extensions

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.createImageUri(): Uri? {
    try {
        val file = File(cacheDir, "camera_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )
    } catch (_: Exception) {
        return null
    }
}