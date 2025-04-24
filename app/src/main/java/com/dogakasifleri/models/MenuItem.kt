package com.dogakasifleri.models

/**
 * Menü Öğesi modeli - Ana menüdeki seçenekleri temsil eder
 */
data class MenuItem(
    val id: Int,
    val title: String,
    val iconResId: Int,
    val description: String
)
