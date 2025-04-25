package com.dogakasifleri.models

/**
 * Ekosistem modeli - Farklı doğal ortamları temsil eder
 */
data class Ecosystem(
        val id: Int,
        val name: String,
        val description: String?,
        val imageResId: Int,
        val colorResId: Int,
        val type: String,
        val species: List<Species>

)
