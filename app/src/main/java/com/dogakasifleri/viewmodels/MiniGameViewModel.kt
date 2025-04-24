package com.dogakasifleri.viewmodels

import android.content.Context
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putLong
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogakasifleri.models.MiniGame
import com.dogakasifleri.game.minigames.MiniGameManager

/**
 * MiniGameViewModel - Mini oyun verilerini yöneten ViewModel sınıfı
 * Bu sınıf, mini oyunlar hakkındaki verileri yönetir ve UI bileşenlerine sunar.
 */
class MiniGameViewModel : ViewModel() {

    private val _miniGame = MutableLiveData<MiniGame?>()
    val miniGame: LiveData<MiniGame> = _miniGame as LiveData<MiniGame>

    private val _allMiniGames = MutableLiveData<List<MiniGame>>()
    val allMiniGames: LiveData<List<MiniGame>> = _allMiniGames

    private val _ecosystemMiniGames = MutableLiveData<List<MiniGame>?>()
    val ecosystemMiniGames: LiveData<List<MiniGame>> = _ecosystemMiniGames as LiveData<List<MiniGame>>

    private val _completedMiniGames = MutableLiveData<List<MiniGame>>()
    val completedMiniGames: LiveData<List<MiniGame>> = _completedMiniGames

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Tüm mini oyunları yükler
     */
    fun getAllMiniGames(): LiveData<List<MiniGame>> {
        _isLoading.value = true
        
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val miniGameList = listOf(
                MiniGame(
                    id = 1,
                    name = "Hayvan Hafıza Oyunu",
                    description = "Hayvanları eşleştirerek hafızanı test et!",
                    type = "Hafıza",
                    difficulty = "Easy",
                    points = 50,
                    imageUrl = "memory_game.png"
                ),
                MiniGame(
                    id = 2,
                    name = "Ekosistem Bulmacası",
                    description = "Parçaları birleştirerek ekosistem resmini tamamla!",
                    type = "Bulmaca",
                    difficulty = "Medium",
                    points = 75,
                    imageUrl = "puzzle_game.png"
                ),
                MiniGame(
                    id = 3,
                    name = "Doğa Bilgi Yarışması",
                    description = "Doğa hakkında sorulara doğru cevap ver!",
                    type = "Quiz",
                    difficulty = "Hard",
                    points = 100,
                    imageUrl = "quiz_game.png"
                ),
                MiniGame(
                    id = 4,
                    name = "Hayvan Sınıflandırma",
                    description = "Hayvanları doğru ekosistemlerine yerleştir!",
                    type = "Sıralama",
                    difficulty = "Medium",
                    points = 75,
                    imageUrl = "sorting_game.png"
                ),
                MiniGame(
                    id = 5,
                    name = "Geri Dönüşüm Oyunu",
                    description = "Atıkları doğru geri dönüşüm kutularına at!",
                    type = "Geri Dönüşüm",
                    difficulty = "Easy",
                    points = 50,
                    imageUrl = "recycling_game.png"
                )
            )
            
            _allMiniGames.value = miniGameList
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Mini oyunlar yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _allMiniGames
    }
    
    /**
     * Belirli bir mini oyunu yükler
     */
    fun getMiniGame(miniGameId: Int): LiveData<MiniGame> {
        _isLoading.value = true
        
        try {
            val allMiniGamesList = _allMiniGames.value ?: getAllMiniGames().value
            
            val miniGame = allMiniGamesList?.find { it.id == miniGameId }
            if (miniGame != null) {
                _miniGame.value = miniGame
            } else {
                _error.value = "Mini oyun bulunamadı"
            }
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Mini oyun yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _miniGame as LiveData<MiniGame>
    }
    
    /**
     * Belirli bir ekosistem için mini oyunları yükler
     */
    fun getMiniGamesForEcosystem(ecosystemType: String): LiveData<List<MiniGame>> {
        _isLoading.value = true
        
        try {
            val allMiniGamesList = _allMiniGames.value ?: getAllMiniGames().value
            
            val filteredMiniGames = allMiniGamesList?.filter { 
                it.ecosystemType == ecosystemType || it.ecosystemType.isEmpty() 
            }
            _ecosystemMiniGames.value = filteredMiniGames ?: listOf()
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Ekosistem mini oyunları yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _ecosystemMiniGames as LiveData<List<MiniGame>>
    }
    
    /**
     * Tamamlanan mini oyunları yükler
     */
    fun getCompletedMiniGames(completedMiniGameIds: List<Int>): LiveData<List<MiniGame>> {
        _isLoading.value = true
        
        try {
            val allMiniGamesList = _allMiniGames.value ?: getAllMiniGames().value
            
            val completedMiniGamesList = allMiniGamesList?.filter { miniGame ->
                completedMiniGameIds.contains(miniGame.id)
            }?.map { miniGame ->
                // Tamamlanmış olarak işaretle
                miniGame.copy(isCompleted = true, completionDate = System.currentTimeMillis())
            }
            
            _completedMiniGames.value = completedMiniGamesList ?: listOf()
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Tamamlanan mini oyunlar yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _completedMiniGames
    }
    
    /**
     * Mini oyunu tamamla
     */
    fun completeMiniGame(gameId: Int, userId: String, score: Int): Boolean {
        return try {
            val miniGame = miniGames.find { it.id == gameId } ?: return false
            val userPrefs = context.getSharedPreferences("User_$userId", Context.MODE_PRIVATE)

            // Skor güncelleme
            val currentHighScore = userPrefs.getInt("highscore_$gameId", 0)
            if (score > currentHighScore) {
                userPrefs.edit {
                    putInt("highscore_$gameId", score)
                }
            }

            // Tamamlama durumu
            userPrefs.edit {
                putBoolean("completed_$gameId", true)
                putLong("completionDate_$gameId", System.currentTimeMillis())
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    // BaseMiniGame sınıfına eksik metod ekleme
    abstract class BaseMiniGame(
        context: Context,
        protected val miniGame: MiniGame
    ) : SurfaceView(context), SurfaceHolder.Callback {

    private fun updateMiniGameLists(updatedGame: MiniGame) {
        // Tüm listeleri güncelleme mantığı
        fun <T> MutableList<T>.updateItem(predicate: (T) -> Boolean, newItem: T) {
            val index = indexOfFirst(predicate)
            if (index != -1) this[index] = newItem
        }

        _allMiniGames.value?.toMutableList()?.apply {
            updateItem({ it.id == updatedGame.id }, updatedGame)
            _allMiniGames.postValue(this)
        }

        _ecosystemMiniGames.value?.toMutableList()?.apply {
            updateItem({ it.id == updatedGame.id }, updatedGame)
            _ecosystemMiniGames.postValue(this)
        }

        _completedMiniGames.value?.toMutableList()?.apply {
            if (none { it.id == updatedGame.id }) add(updatedGame)
            _completedMiniGames.postValue(this)
        }
    }
}

private fun MiniGameManager.completeMiniGame(
    i: Int,
    i: Int,
    string: String
) {
}
