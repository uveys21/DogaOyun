package com.dogakasifleri.utils

import android.content.Context
import com.dogakasifleri.R
import com.dogakasifleri.models.Achievement

/**
 * Başarı Yöneticisi - Kullanıcının kazandığı başarıları ve rozetleri yönetir
 */
class AchievementManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("Achievements", Context.MODE_PRIVATE)

    /**
     * Başarının kilidini açar
     */
    fun unlockAchievement(achievementId: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(achievementId, true)
        editor.apply()
        
        // Gerçek uygulamada burada bildirim gösterilebilir
    }

    /**
     * Başarının kilidinin açık olup olmadığını kontrol eder
     */
    fun isAchievementUnlocked(achievementId: String): Boolean {
        return sharedPreferences.getBoolean(achievementId, false)
    }

    /**
     * Kilidi açılmış tüm başarıların ID'lerini döndürür
     */
    fun getUnlockedAchievements(): List<String> {
        val unlockedAchievements = mutableListOf<String>()
        
        // Tüm kayıtları kontrol et
        val allEntries = sharedPreferences.all
        
        for (entry in allEntries) {
            // Başarı ID'lerini ayıkla
            if (entry.value == true) {
                unlockedAchievements.add(entry.key)
            }
        }
        
        return unlockedAchievements
    }

    /**
     * Açılan başarı sayısını döndürür
     */
    fun getUnlockedAchievementCount(): Int {
        return getUnlockedAchievements().size
    }

    /**
     * Tüm başarıları sıfırlar
     */
    fun resetAchievements() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

      fun getAllAchievements(context: Context): List<Achievement> {
        return listOf(
            Achievement(
                id = "explorer", 
                title = "Kaşif", 
                description = "5 farklı türü keşfet", 
                iconResId = R.drawable.achievement_explorer,
                maxProgress = 5,
                progress = 0,
                completed = false 
            ),
            Achievement(
                id = "collector", 
                title = "Koleksiyoncu", 
                description = "10 farklı türü koleksiyonuna ekle", 
                iconResId = R.drawable.achievement_collector,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                id = "master_collector", 
                title = "Usta Koleksiyoncu", 
                description = "Tüm türleri koleksiyonuna ekle", 
                iconResId = R.drawable.achievement_master_collector,
                maxProgress = 16,
                progress = 0,
                completed = false
            ),
            Achievement(
                id = "forest_expert", 
                title = "Orman Uzmanı", 
                description = "Orman ekosistemindeki tüm türleri keşfet", 
                iconResId = R.drawable.achievement_forest_expert,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
             Achievement(
                id = "ocean_expert",
                name = "Okyanus Uzmanı",
                description = "Okyanus ekosistemindeki tüm türleri keşfet", 
                iconResId = R.drawable.achievement_ocean_expert,
                maxProgress = 4,
                progress = 0,
                completed = false
            ),
            // Diğer başarılar...
        ).map { achievement ->
            achievement.copy(isUnlocked = isAchievementUnlocked(achievement.id))
        }
    }

    /**
     * Koleksiyon durumunu kontrol eder ve ilgili başarıları açar
     */
    fun checkCollectionAchievements(collectionManager: CollectionManager) {
        val collectionCount = collectionManager.getCollectionCount()

        // Başarıları kontrol et
        if (collectionCount >= 5) {
            unlockAchievement("explorer")
        }

        if (collectionCount >= 10) {
             unlockAchievement("collector")
        }

        if (collectionCount >= 16) { // Tüm türler (4 ekosistem x 4 tür)
            unlockAchievement("master_collector")
        }

        // Ekosistem başarılarını kontrol et
        checkEcosystemAchievements(collectionManager)
    }

    /**
     * Ekosistem başarılarını kontrol eder
     */
    private fun checkEcosystemAchievements(collectionManager: CollectionManager) {
        val collectedSpecies = collectionManager.getCollectedSpecies()
        
        // Orman türleri (ID: 1-4)
        val forestSpecies = collectedSpecies.filter { it.speciesId in 1..4 }
        if (forestSpecies.size == 4) {
             unlockAchievement("forest_expert")
        }

        // Okyanus türleri (ID: 5-8)
        val oceanSpecies = collectedSpecies.filter { it.speciesId in 5..8 }
        if (oceanSpecies.size == 4) {
             unlockAchievement("ocean_expert")
        }

        // Çöl türleri (ID: 9-12)
        val desertSpecies = collectedSpecies.filter { it.speciesId in 9..12 }
        if (desertSpecies.size == 4) {
             unlockAchievement("desert_expert")
        }

        // Kutup türleri (ID: 13-16)
        val arcticSpecies = collectedSpecies.filter { it.speciesId in 13..16 }
        if (arcticSpecies.size == 4) {
             unlockAchievement("arctic_expert")
        }
    }
}
