package com.example.heysports.data.models.enums

enum class ETimeOption(
    val label: String,
    val hour: Int,
    val minute: Int,
    val isPopular: Boolean = false,
) {
    TIME_05_00("05:00", 5, 0),
    TIME_05_30("05:30", 5, 30, true),
    TIME_06_00("06:00", 6, 0, true),
    TIME_06_30("06:30", 6, 30, true),
    TIME_07_00("07:00", 7, 0),
    TIME_16_30("16:30", 16, 30),
    TIME_17_00("17:00", 17, 0),
    TIME_17_30("17:30", 17, 30, true),
    TIME_18_00("18:00", 18, 0),
    TIME_18_30("18:30", 18, 30, true),
    TIME_19_00("19:00", 19, 0, true),
    TIME_19_30("19:30", 19, 30, true),
    TIME_20_00("20:00", 20, 0),
    TIME_20_30("20:30", 20, 30);
    ;
}