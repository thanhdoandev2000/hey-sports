package com.example.heysports.cores.utils

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Phone", showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Preview(name = "Tablet", showBackground = true, showSystemUi = true, device = Devices.PIXEL_C)
@Preview(name = "Phone", showBackground = true, showSystemUi = true, device = Devices.PIXEL_3)
annotation class AppPreview