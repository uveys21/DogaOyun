package com.dogakasifleri.models

import java.io.Serializable

/**
 * Quest - Kullanıcının tamamlayabileceği görevleri temsil eden model sınıfı
 * Bu sınıf, görevlerin özellikleri, tamamlanma koşulları ve ödülleri hakkında bilgileri içerir.
 */
data class Quest(
    val id: Int,
    val title: String,
    val description: String,
    val ecosystemType: String,
    val difficulty: String, // Easy, Medium, Hard
    val points: Int,
    val requiredSpeciesIds: List<Int> = listOf(),
    val requiredActions: List<String> = listOf(),
    val imageUrl: String = "",
    val isCompleted: Boolean = false,
    val completionDate: Long = 0,
    val unlockRequirements: List<Int> = listOf(), // Önceden tamamlanması gereken görev ID'leri
    val rewards: List<String> = listOf(),
    val timeLimit: Long = 0 // 0 = sınırsız süre, diğer değerler milisaniye cinsinden süre
) : Serializable {
    
    /**
     * Görevin zorluk seviyesine göre renk kodunu döndürür
     */
    fun getDifficultyColor(): String {
        return when (difficulty) {
            "Easy" -> "#00C000" // Yeşil
            "Medium" -> "#FFA500" // Turuncu
            "Hard" -> "#FF0000" // Kırmızı
            else -> "#00C000"
        }
    }
    
    /**
     * Görevin zorluk seviyesini Türkçe olarak döndürür
     */
    fun getDifficultyText(): String {
        return when (difficulty) {
            "Easy" -> "Kolay"
            "Medium" -> "Orta"
            "Hard" -> "Zor"
            else -> "Kolay"
        }
    }
    
    /**
     * Görevin ekosistem tipine göre arka plan resmini döndürür
     */
    fun getEcosystemBackground(): String {
        return when (ecosystemType) {
            "Orman" -> "bg_ecosystem_forest.xml"
            "Okyanus" -> "bg_ecosystem_ocean.xml"
            "Çöl" -> "bg_ecosystem_desert.xml"
            "Kutup" -> "bg_ecosystem_arctic.xml"
            else -> "bg_ecosystem_forest.xml"
        }
    }
    
    /**
     * Görevin ödüllerini açıklayan metni döndürür
     */
    fun getRewardsText(): String {
        if (rewards.isEmpty()) return "Bu görevin özel bir ödülü yoktur."
        return rewards.joinToString(", ")
    }
    
    /**
     * Görevin zaman sınırını insan tarafından okunabilir formatta döndürür
     */
    fun getFormattedTimeLimit(): String {
        if (timeLimit == 0L) return "Sınırsız süre"
        
        val minutes = timeLimit / 60000
        val seconds = (timeLimit % 60000) / 1000
        
        return when {
            minutes > 0 -> "$minutes dakika $seconds saniye"
            else -> "$seconds saniye"
        }
    }
    
    /**
     * Görevin tamamlanma durumunu kontrol eder
     */
    fun checkCompletion(discoveredSpeciesIds: List<Int>, completedActions: List<String>): Boolean {
        // Tüm gerekli türlerin keşfedilip keşfedilmediğini kontrol et
        val allSpeciesDiscovered = requiredSpeciesIds.all { speciesId ->
            discoveredSpeciesIds.contains(speciesId)
        }
        
        // Tüm gerekli eylemlerin tamamlanıp tamamlanmadığını kontrol et
        val allActionsCompleted = requiredActions.all { action ->
            completedActions.contains(action)
        }
        
        // Her iki koşul da sağlanıyorsa görev tamamlanmıştır
        return allSpeciesDiscovered && allActionsCompleted
    }
}
