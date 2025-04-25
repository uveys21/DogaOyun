package com.dogakasifleri.models

/**
 * Tür modeli - Ekosistemlerdeki bitki ve hayvan türlerini temsil eder
 */
data class Species(
    val id: Int,
    val name: String,
    val scientificName: String,
    val type: String,
    val shortDescription: String,
    val imageResId: Int,
    val description: String,
    val facts: String,
    val level: Int,
    val experience: Int
)
