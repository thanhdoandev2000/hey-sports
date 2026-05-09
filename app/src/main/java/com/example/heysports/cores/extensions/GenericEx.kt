package com.example.heysports.cores.extensions

inline fun <reified T> Any?.castTo(): T? = this as? T