package com.dogakasifleri.models

/**
 * Ekosistem modeli - Farklı doğal ortamları temsil eder
 */
data class Ecosystem(
    val id: Int,
    val colorResId: String,
    val name: String,
    val description: String?,
    val imageResId: String,
    val imageResource: Int
)
