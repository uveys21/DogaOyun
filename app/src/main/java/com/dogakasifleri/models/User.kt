package com.dogakasifleri.models

import java.io.Serializable

/**
 * User - Kullanıcı bilgilerini tutan model sınıfı
 * Bu sınıf, kullanıcının adı, yaşı, avatarı ve ilerleme durumu gibi bilgileri içerir.
 */
data class User(
    val id: String,
    var name: String,
    var age: Int,
    var avatarId: Int,
    var points: Int = 0,
    var level: Int = 1,
    var lastLoginDate: Long = System.currentTimeMillis(),
    var totalPlayTime: Long = 0,
    var parentalControlEnabled: Boolean = false,
    var collectedSpeciesIds: List<Int> = listOf(),
    var completedQuestIds: List<Int> = listOf(),
    var earnedAchievementIds: List<Int> = listOf(),
    var visitedEcosystemIds: List<Int> = listOf()
) : Serializable {
    
    /**
     * Kullanıcının seviyesini hesaplar
     */
    fun calculateLevel(): Int {
        // Her 100 puan için 1 seviye
        return (points / 100) + 1
    }
    
    /**
     * Kullanıcının bir sonraki seviyeye geçmesi için gereken puanı hesaplar
     */
    fun pointsToNextLevel(): Int {
        return (level * 100) - points
    }
    
    /**
     * Kullanıcıya puan ekler ve seviyesini günceller
     */
    fun addPoints(newPoints: Int) {
        points += newPoints
        level = calculateLevel()
    }
    
    /**
     * Kullanıcının yaş grubuna göre içerik filtreleme seviyesini döndürür
     */
    fun getContentFilterLevel(): String {
        return when {
            age < 7 -> "Kolay"
            age < 10 -> "Orta"
            else -> "Zor"
        }
    }
    
    /**
     * Kullanıcının koleksiyon tamamlanma yüzdesini hesaplar
     */
    fun calculateCollectionCompletionPercentage(totalSpeciesCount: Int): Int {
        if (totalSpeciesCount == 0) return 0
        return (collectedSpeciesIds.size * 100) / totalSpeciesCount
    }
    
    /**
     * Kullanıcının görev tamamlanma yüzdesini hesaplar
     */
    fun calculateQuestCompletionPercentage(totalQuestCount: Int): Int {
        if (totalQuestCount == 0) return 0
        return (completedQuestIds.size * 100) / totalQuestCount
    }
    
    /**
     * Kullanıcının başarı tamamlanma yüzdesini hesaplar
     */
    fun calculateAchievementCompletionPercentage(totalAchievementCount: Int): Int {
        if (totalAchievementCount == 0) return 0
        return (earnedAchievementIds.size * 100) / totalAchievementCount
    }
    
    /**
     * Kullanıcının toplam oyun süresini insan tarafından okunabilir formatta döndürür
     */
    fun getFormattedPlayTime(): String {
        val hours = totalPlayTime / 3600000
        val minutes = (totalPlayTime % 3600000) / 60000
        
        return when {
            hours > 0 -> "$hours saat $minutes dakika"
            else -> "$minutes dakika"
        }
    }
}
