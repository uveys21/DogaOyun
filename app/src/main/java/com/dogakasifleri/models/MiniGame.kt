package com.dogakasifleri.models

import java.io.Serializable

/**
 * MiniGame - Mini oyunları temsil eden model sınıfı
 * Bu sınıf, mini oyunların özellikleri, zorluk seviyeleri ve ödülleri hakkında bilgileri içerir.
 */
data class MiniGame(
    val id: Int,
    val name: String,
    val description: String,
    val type: GameType,
    val difficulty: GameDifficulty,
    val ecosystemId: Int?,
    val imageResource: Int,
    val rewardPoints: Int,
    var isCompleted: Boolean = false,
    var highScore: Int = 0,
    var completionDate: Long = 0,
    val points: Int,
    val imageUrl: String = "",
    val ecosystemType: String = "",
    val unlockRequirements: List<Int> = listOf(), // Önceden tamamlanması gereken oyun ID'leri
    val rewards: List<String> = listOf(),
    val timeLimit: Long = 0 // 0 = sınırsız süre, diğer değerler milisaniye cinsinden süre
) : Serializable {


    
    /**
     * Oyunun zorluk seviyesine göre renk kodunu döndürür
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
     * Oyunun zorluk seviyesini Türkçe olarak döndürür
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
     * Oyunun tipine göre buton arka planını döndürür
     */
    fun getGameButtonBackground(): String {
        return when (type) {
            "Hafıza" -> "bg_button_blue"
            "Bulmaca" -> "bg_button_green"
            "Quiz" -> "bg_button_orange"
            "Sıralama" -> "bg_button_purple"
            "Geri Dönüşüm" -> "bg_button_green"
            else -> "bg_button_blue"
        }
    }
    
    /**
     * Oyunun ödüllerini açıklayan metni döndürür
     */
    fun getRewardsText(): String {
        if (rewards.isEmpty()) return "Bu oyunun özel bir ödülü yoktur."
        return rewards.joinToString(", ")
    }
    
    /**
     * Oyunun zaman sınırını insan tarafından okunabilir formatta döndürür
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
     * Oyunun tipine göre açıklama metni döndürür
     */
    fun getTypeDescription(): String {
        return when (type) {
            "Hafıza" -> "Eşleştirme kartlarını bularak hafızanı test et!"
            "Bulmaca" -> "Parçaları birleştirerek resmi tamamla!"
            "Quiz" -> "Sorulara doğru cevap vererek bilgini göster!"
            "Sıralama" -> "Nesneleri doğru kategorilere ayır!"
            "Geri Dönüşüm" -> "Atıkları doğru geri dönüşüm kutularına at!"
            else -> "Eğlenceli bir oyun!"
        }
    }

}

