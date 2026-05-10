package com.example.heysports.data.models.enums

enum class EMatchType(val label: String, val playerCount: Int) {
    FIVE_VS_FIVE("5 vs 5", 5),
    SIX_VS_SIX("6 vs 6", 6),
    SEVEN_VS_SEVEN("7 vs 7", 7),
    NINE_VS_NINE("9 vs 9", 9),
    ELEVEN_VS_ELEVEN("11 vs 11", 11),
    FRIENDLY("Giao hữu", 0);

    companion object {
        fun fromLabel(label: String) = entries.find { it.label == label } ?: FIVE_VS_FIVE
    }
}