package com.dogakasifleri.models

/**
 * Tür modeli - Ekosistemlerdeki bitki ve hayvan türlerini temsil eder
 */
data class Species(
    val id: Int,
    val name: String,
    val imageResId1: String,
    val imageResId2: String,
    val imageResId3: String,
    val imageResId: String,
    val description: String,
    val facts: String,
    val ecosystemId: Int,
    val imageResource: Int
)
