package com.example.heysports.cores.extensions

import androidx.navigation.NavController

fun <T: Any>NavController.navigateSingleTop(
    route: T,
    popUpToRoute: String? = null,
    inclusive: Boolean = true
) {
    this.navigate(route) {
        popUpToRoute?.let {
            popUpTo(it) {
                this.inclusive = inclusive
            }
        }
        launchSingleTop = true
        restoreState = false
    }
}