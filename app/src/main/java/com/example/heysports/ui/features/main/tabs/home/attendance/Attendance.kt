package com.example.heysports.ui.features.main.tabs.home.attendance

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.heysports.R
import com.example.heysports.cores.utils.AppPreview
import com.example.heysports.ui.base.HeySportContainer

@Composable
fun AttendanceMatch() {
    AttendanceMatchScreen()
}

@Composable
private fun AttendanceMatchScreen() {
    HeySportContainer(title = stringResource(R.string.homeAttendance)) {

    }
}

@Composable
@Preview
@AppPreview
private fun AttendanceMatchPreview() {
    AttendanceMatchScreen()
}