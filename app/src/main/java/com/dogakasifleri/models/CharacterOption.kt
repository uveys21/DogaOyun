package com.dogakasifleri.models

/**
 * Karakter Seçeneği modeli - Kullanıcının seçebileceği karakterleri temsil eder
 */
data class CharacterOption(
    val id: Int,    
    val name: String,
    val imageResId: Int,
    val description: String
)
