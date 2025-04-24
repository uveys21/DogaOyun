package com.dogakasifleri.game.quests

import android.content.Context
import com.dogakasifleri.models.Quest
import com.dogakasifleri.models.Species
import com.dogakasifleri.utils.DatabaseHelper
import com.dogakasifleri.utils.PreferenceManager
import com.dogakasifleri.utils.SoundManager

/**
 * QuestManager - Görevleri yöneten sınıf
 * Bu sınıf, oyun içi görevleri yükler, takip eder ve tamamlanma durumlarını yönetir.
 */
class QuestManager(private val context: Context?) {

    private val quests = mutableListOf<Quest>()
    private val completedQuestIds = mutableSetOf<Int>()
    private val activeQuestIds = mutableSetOf<Int>()
    private val questListeners = mutableListOf<QuestListener>()
    
    private var databaseHelper: DatabaseHelper? = null
    private var preferenceManager: PreferenceManager? = null
    private var soundManager: SoundManager? = null
    
    init {
        context?.let {
            databaseHelper = DatabaseHelper(it)
            preferenceManager = PreferenceManager(it)
            soundManager = SoundManager(it)
            
            // Tamamlanan görevleri yükle
            loadCompletedQuests()
        }
    }
    
    /**
     * Tüm görevleri yükler
     */
    fun loadAllQuests(): List<Quest> {
        if (quests.isEmpty()) {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            quests.addAll(
                listOf(
                    Quest(
                        id = 1,
                        title = "Orman Kaşifi",
                        description = "Amazon Yağmur Ormanı'ndaki 3 farklı hayvan türünü keşfet",
                        ecosystemType = "Orman",
                        difficulty = "Easy",
                        points = 100,
                        requiredSpeciesIds = listOf(1, 2),
                        imageUrl = "quest_forest.png"
                    ),
                    Quest(
                        id = 2,
                        title = "Deniz Araştırmacısı",
                        description = "Büyük Mercan Resifi'ndeki 2 farklı deniz canlısını keşfet",
                        ecosystemType = "Okyanus",
                        difficulty = "Medium",
                        points = 150,
                        requiredSpeciesIds = listOf(3, 4),
                        imageUrl = "quest_ocean.png"
                    ),
                    Quest(
                        id = 3,
                        title = "Çöl Gezgini",
                        description = "Sahra Çölü'ndeki tüm bitki ve hayvanları keşfet",
                        ecosystemType = "Çöl",
                        difficulty = "Hard",
                        points = 200,
                        requiredSpeciesIds = listOf(5, 6),
                        imageUrl = "quest_desert.png"
                    ),
                    Quest(
                        id = 4,
                        title = "Kutup Kâşifi",
                        description = "Antarktika'daki tüm hayvanları keşfet",
                        ecosystemType = "Kutup",
                        difficulty = "Medium",
                        points = 150,
                        requiredSpeciesIds = listOf(7, 8),
                        imageUrl = "quest_arctic.png"
                    )
                )
            )
        }
        
        return quests
    }
    
    /**
     * Tamamlanan görevleri yükler
     */
    private fun loadCompletedQuests() {
        val userId = preferenceManager?.getUserId() ?: return
        
        // Normalde bu veriler bir veritabanından veya API'den gelir
        // Şimdilik örnek veriler kullanıyoruz
        val completedQuestIdsString = preferenceManager?.preferences?.getString("completed_quests_$userId", "") ?: ""
        
        if (completedQuestIdsString.isNotEmpty()) {
            completedQuestIds.addAll(completedQuestIdsString.split(",").map { it.toInt() })
        }
    }
    
    /**
     * Aktif görevleri yükler
     */
    fun loadActiveQuests(): List<Quest> {
        val userId = preferenceManager?.getUserId() ?: return emptyList()
        
        // Normalde bu veriler bir veritabanından veya API'den gelir
        // Şimdilik örnek veriler kullanıyoruz
        val activeQuestIdsString = preferenceManager?.preferences?.getString("active_quests_$userId", "") ?: ""
        
        if (activeQuestIdsString.isNotEmpty()) {
            activeQuestIds.addAll(activeQuestIdsString.split(",").map { it.toInt() })
        }
        
        val allQuests = loadAllQuests()
        return allQuests.filter { activeQuestIds.contains(it.id) }
    }
    
    /**
     * Belirli bir ekosistem için görevleri yükler
     */
    fun getQuestsForEcosystem(ecosystemType: String): List<Quest> {
        val allQuests = loadAllQuests()
        return allQuests.filter { it.ecosystemType == ecosystemType }
    }
    
    /**
     * Tamamlanan görevleri döndürür
     */
    fun getCompletedQuests(): List<Quest> {
        val allQuests = loadAllQuests()
        return allQuests.filter { completedQuestIds.contains(it.id) }
    }
    
    /**
     * Görevi aktifleştirir
     */
    fun activateQuest(questId: Int): Boolean {
        val userId = preferenceManager?.getUserId() ?: return false
        
        if (!activeQuestIds.contains(questId)) {
            activeQuestIds.add(questId)
            
            // Aktif görevleri kaydet
            val activeQuestIdsString = activeQuestIds.joinToString(",")
            preferenceManager?.editor?.putString("active_quests_$userId", activeQuestIdsString)
            preferenceManager?.editor?.apply()
            
            // Görev aktifleştirildi olayını tetikle
            val quest = quests.find { it.id == questId }
            if (quest != null) {
                notifyQuestActivated(quest)
            }
            
            return true
        }
        
        return false
    }
    
    /**
     * Görevi tamamlar
     */
    fun completeQuest(questId: Int, userId: String): Boolean {
        if (completedQuestIds.contains(questId)) {
            return false // Zaten tamamlanmış
        }
        
        val quest = quests.find { it.id == questId } ?: return false
        
        // Görevi tamamla
        completedQuestIds.add(questId)
        
        // Aktif görevlerden kaldır
        activeQuestIds.remove(questId)
        
        // Tamamlanan görevleri kaydet
        val completedQuestIdsString = completedQuestIds.joinToString(",")
        preferenceManager?.editor?.putString("completed_quests_$userId", completedQuestIdsString)
        
        // Aktif görevleri güncelle
        val activeQuestIdsString = activeQuestIds.joinToString(",")
        preferenceManager?.editor?.putString("active_quests_$userId", activeQuestIdsString)
        
        preferenceManager?.editor?.apply()
        
        // Görev tamamlandı olayını tetikle
        notifyQuestCompleted(quest)
        
        // Ses efekti çal
        soundManager?.playSound("quest_complete")
        
        return true
    }
    
    /**
     * Görevin tamamlanıp tamamlanmadığını kontrol eder
     */
    fun isQuestCompleted(questId: Int): Boolean {
        return completedQuestIds.contains(questId)
    }
    
    /**
     * Görevin aktif olup olmadığını kontrol eder
     */
    fun isQuestActive(questId: Int): Boolean {
        return activeQuestIds.contains(questId)
    }
    
    /**
     * Görev ilerleme durumunu kontrol eder
     */
    fun checkQuestProgress(questId: Int, discoveredSpeciesIds: List<Int>): Float {
        val quest = quests.find { it.id == questId } ?: return 0f
        
        val requiredSpeciesIds = quest.requiredSpeciesIds
        if (requiredSpeciesIds.isEmpty()) return 0f
        
        val discoveredRequired = requiredSpeciesIds.filter { discoveredSpeciesIds.contains(it) }
        return discoveredRequired.size.toFloat() / requiredSpeciesIds.size
    }
    
    /**
     * Tür keşfedildiğinde görev ilerlemesini günceller
     */
    fun onSpeciesDiscovered(species: Species, userId: String) {
        // Tüm aktif görevleri kontrol et
        val activeQuests = loadActiveQuests()
        
        for (quest in activeQuests) {
            if (quest.requiredSpeciesIds.contains(species.id)) {
                // Bu tür, görev için gerekli
                
                // Görev ilerleme durumunu kontrol et
                val discoveredSpeciesIds = getDiscoveredSpeciesIds(userId)
                val progress = checkQuestProgress(quest.id, discoveredSpeciesIds)
                
                // Görev ilerleme durumunu güncelle
                notifyQuestProgress(quest, progress)
                
                // Görev tamamlandı mı?
                if (progress >= 1.0f) {
                    completeQuest(quest.id, userId)
                }
            }
        }
    }
    
    /**
     * Kullanıcının keşfettiği türlerin ID'lerini döndürür
     */
    private fun getDiscoveredSpeciesIds(userId: String): List<Int> {
        // Normalde bu veriler bir veritabanından veya API'den gelir
        // Şimdilik örnek veriler kullanıyoruz
        val discoveredSpeciesIdsString = preferenceManager?.preferences?.getString("discovered_species_$userId", "") ?: ""
        
        return if (discoveredSpeciesIdsString.isNotEmpty()) {
            discoveredSpeciesIdsString.split(",").map { it.toInt() }
        } else {
            emptyList()
        }
    }
    
    /**
     * Görev dinleyicisi ekler
     */
    fun addQuestListener(listener: QuestListener) {
        questListeners.add(listener)
    }
    
    /**
     * Görev dinleyicisini kaldırır
     */
    fun removeQuestListener(listener: QuestListener) {
        questListeners.remove(listener)
    }
    
    /**
     * Görev aktifleştirildi olayını tetikler
     */
    private fun notifyQuestActivated(quest: Quest) {
        for (listener in questListeners) {
            listener.onQuestActivated(quest)
        }
    }
    
    /**
     * Görev ilerleme durumu olayını tetikler
     */
    private fun notifyQuestProgress(quest: Quest, progress: Float) {
        for (listener in questListeners) {
            listener.onQuestProgress(quest, progress)
        }
    }
    
    /**
     * Görev tamamlandı olayını tetikler
     */
    private fun notifyQuestCompleted(quest: Quest) {
        for (listener in questListeners) {
            listener.onQuestCompleted(quest)
        }
    }
    
    /**
     * Görev dinleyicisi arayüzü
     */
    interface QuestListener {
        fun onQuestActivated(quest: Quest)
        fun onQuestProgress(quest: Quest, progress: Float)
        fun onQuestCompleted(quest: Quest)
    }
}
