package com.dogakasifleri.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogakasifleri.models.Quest
import com.dogakasifleri.game.quests.QuestManager

/**
 * QuestViewModel - Görev verilerini yöneten ViewModel sınıfı
 * Bu sınıf, görevler hakkındaki verileri yönetir ve UI bileşenlerine sunar.
 */
class QuestViewModel : ViewModel() {

    private val _quest = MutableLiveData<Quest?>()
    val quest: LiveData<Quest> = _quest as LiveData<Quest>

    private val _allQuests = MutableLiveData<List<Quest>>()
    val allQuests: LiveData<List<Quest>> = _allQuests

    private val _ecosystemQuests = MutableLiveData<List<Quest>?>()
    val ecosystemQuests: LiveData<List<Quest>> = _ecosystemQuests as LiveData<List<Quest>>

    private val _completedQuests = MutableLiveData<List<Quest>>()
    val completedQuests: LiveData<List<Quest>> = _completedQuests

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Tüm görevleri yükler
     */
    fun getAllQuests(): LiveData<List<Quest>> {
        _isLoading.value = true
        
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val questList = listOf(
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
            
            _allQuests.value = questList
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Görevler yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _allQuests
    }
    
    /**
     * Belirli bir görevi yükler
     */
    fun getQuest(questId: Int): LiveData<Quest> {
        _isLoading.value = true
        
        try {
            val allQuestsList = _allQuests.value ?: getAllQuests().value
            
            val quest = allQuestsList?.find { it.id == questId }
            if (quest != null) {
                _quest.value = quest
            } else {
                _error.value = "Görev bulunamadı"
            }
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Görev yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _quest as LiveData<Quest>
    }
    
    /**
     * Belirli bir ekosistem için görevleri yükler
     */
    fun getQuestsForEcosystem(ecosystemType: String): LiveData<List<Quest>> {
        _isLoading.value = true
        
        try {
            val allQuestsList = _allQuests.value ?: getAllQuests().value
            
            val filteredQuests = allQuestsList?.filter { it.ecosystemType == ecosystemType }
            _ecosystemQuests.value = filteredQuests ?: listOf()
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Ekosistem görevleri yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _ecosystemQuests as LiveData<List<Quest>>
    }
    
    /**
     * Tamamlanan görevleri yükler
     */
    fun getCompletedQuests(completedQuestIds: List<Int>): LiveData<List<Quest>> {
        _isLoading.value = true
        
        try {
            val allQuestsList = _allQuests.value ?: getAllQuests().value
            
            val completedQuestsList = allQuestsList?.filter { quest ->
                completedQuestIds.contains(quest.id)
            }?.map { quest ->
                // Tamamlanmış olarak işaretle
                quest.copy(isCompleted = true, completionDate = System.currentTimeMillis())
            }
            
            _completedQuests.value = completedQuestsList ?: listOf()
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Tamamlanan görevler yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _completedQuests
    }
    
    /**
     * Görevi tamamla
     */
    fun completeQuest(questId: Int, userId: String): Boolean {
        try {
            val quest = getQuest(questId).value ?: return false
            
            // Görev tamamlama işlemleri
            // Normalde bu işlemler bir veritabanına veya API'ye kaydedilir
            
            // Görev yöneticisini kullanarak görevi tamamla
            val questManager = QuestManager(null) // Context null olarak geçildi, gerçek uygulamada context verilmeli
            val isCompleted = questManager.completeQuest(questId, userId)
            
            if (isCompleted) {
                // Tamamlanan görevi güncelle
                val updatedQuest = quest.copy(
                    isCompleted = true,
                    completionDate = System.currentTimeMillis()
                )
                _quest.value = updatedQuest
                
                // Tüm görevler listesini güncelle
                val allQuestsList = _allQuests.value?.toMutableList() ?: mutableListOf()
                val index = allQuestsList.indexOfFirst { it.id == questId }
                if (index != -1) {
                    allQuestsList[index] = updatedQuest
                    _allQuests.value = allQuestsList
                }
                
                // Ekosistem görevleri listesini güncelle
                val ecosystemQuestsList = _ecosystemQuests.value?.toMutableList()
                if (ecosystemQuestsList != null) {
                    val ecosystemIndex = ecosystemQuestsList.indexOfFirst { it.id == questId }
                    if (ecosystemIndex != -1) {
                        ecosystemQuestsList[ecosystemIndex] = updatedQuest
                        _ecosystemQuests.value = ecosystemQuestsList
                    }
                }
                
                // Tamamlanan görevler listesini güncelle
                val completedQuestsList = _completedQuests.value?.toMutableList() ?: mutableListOf()
                if (!completedQuestsList.any { it.id == questId }) {
                    completedQuestsList.add(updatedQuest)
                    _completedQuests.value = completedQuestsList
                }
                
                return true
            }
            
            return false
        } catch (e: Exception) {
            _error.value = "Görev tamamlanırken hata oluştu: ${e.message}"
            return false
        }
    }
}
