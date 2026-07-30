package com.example.heysports.cores.extensions

import java.util.Locale

internal fun Int?.asStatText(): String = this?.toString() ?: "—"

internal fun Double?.asRatingText(): String {
    return this?.let { String.format(Locale.US, "%.1f", it) } ?: "—"
}
