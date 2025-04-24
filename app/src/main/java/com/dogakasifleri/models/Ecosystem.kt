package com.dogakasifleri.models

/**
 * Ekosistem modeli - Farklı doğal ortamları temsil eder
 */
data class Ecosystem(
        val id: Int,
        val name: String,
        val description: String?,
        val type: String,
        val species: List<Species>

)
