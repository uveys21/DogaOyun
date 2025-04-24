package com.dogakasifleri.models

/**
 * Başarı modeli - Kullanıcının kazanabileceği rozetleri temsil eder
 */
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconResId: Int,
    val isUnlocked: Boolean = false,
    val maxProgress: Int,
    val progress: Int,
    val completed: Boolean
)
