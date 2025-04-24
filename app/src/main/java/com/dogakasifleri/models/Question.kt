package com.dogakasifleri.models

/**
 * Soru modeli - Quiz'lerdeki soruları temsil eder
 */
data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
