package com.example.heysports.cores.extensions

internal fun String?.getValue(default: String = ""): String = this ?: default

internal fun String?.orDefaultIfBlank(default: String): String {
    return this?.takeIf(String::isNotBlank) ?: default
}
