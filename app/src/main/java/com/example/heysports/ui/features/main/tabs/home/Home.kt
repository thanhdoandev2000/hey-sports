package com.example.heysports.ui.features.main.tabs.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.components.cores.JPText

@Composable
fun Home() {
    Column() {
        JPText(text = "Home")
    }
}

@Composable
private fun HomeScreen() {

}

@Composable
@Preview
@AppPreview
private fun HomePreview() {
    HomeScreen()
}