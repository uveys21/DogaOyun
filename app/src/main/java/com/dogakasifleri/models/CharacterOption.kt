package com.dogakasifleri.models

/**
 * Karakter Seçeneği modeli - Kullanıcının seçebileceği karakterleri temsil eder
 */
data class CharacterOption(
    val id: Int,
    val imageResId1: String,
    val imageResId: String,
    val name: String
)
