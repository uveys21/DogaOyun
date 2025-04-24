package com.dogakasifleri.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogakasifleri.models.User
import com.dogakasifleri.models.Character
import com.dogakasifleri.models.Ecosystem
import com.dogakasifleri.models.Species

/**
 * MainViewModel - Ana uygulama verilerini yöneten ViewModel sınıfı
 * Bu sınıf, kullanıcı bilgileri, karakter, ekosistemler ve türler gibi
 * ana uygulama verilerini yönetir ve UI bileşenlerine sunar.
 */
class MainViewModel : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser

    private val _userCharacter = MutableLiveData<Character>()
    val userCharacter: LiveData<Character> = _userCharacter

    private val _ecosystems = MutableLiveData<List<Ecosystem>>()
    val ecosystems: LiveData<List<Ecosystem>> = _ecosystems

    private val _allSpecies = MutableLiveData<List<Species>>()
    val allSpecies: LiveData<List<Species>> = _allSpecies

    private val _discoveredSpecies = MutableLiveData<List<Species>>()
    val discoveredSpecies: LiveData<List<Species>> = _discoveredSpecies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Kullanıcı verilerini yükler
     */
    fun loadUserData(userId: String) {
        _isLoading.value = true
        
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val user = User(
                id = userId,
                name = "Doğa Kaşifi",
                age = 8,
                avatarId = 1,
                points = 250,
                level = 3,
                parentalControlEnabled = true,
                collectedSpeciesIds = listOf(1, 2, 3, 5, 8),
                completedQuestIds = listOf(1, 2),
                earnedAchievementIds = listOf(1, 3, 5),
                visitedEcosystemIds = listOf(1, 2)
            )
            
            _currentUser.value = user
            
            // Kullanıcı karakterini yükle
            loadUserCharacter(userId)
            
            // Ekosistemleri yükle
            loadEcosystems()
            
            // Türleri yükle
            loadAllSpecies()
            
            // Keşfedilen türleri filtrele
            filterDiscoveredSpecies()
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Kullanıcı verileri yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
    }
    
    /**
     * Kullanıcı karakterini yükler
     */
    private fun loadUserCharacter(userId: String) {
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val character = Character(
                id = 1,
                name = "Doğa Kaşifi",
                avatarId = 1,
                skinTone = "medium",
                hairColor = "brown",
                hairStyle = "short",
                eyeColor = "brown",
                clothesColor = "green",
                accessoryId = 2,
                specialAbility = "explorer",
                userId = userId
            )
            
            _userCharacter.value = character
        } catch (e: Exception) {
            _error.value = "Karakter verileri yüklenirken hata oluştu: ${e.message}"
        }
    }
    
    /**
     * Ekosistemleri yükler
     */
    private fun loadEcosystems() {
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val ecosystemList = listOf(
                Ecosystem(
                    1,
                    "Amazon Yağmur Ormanı",
                    "Orman",
                    null,
                    "Dünyanın en büyük yağmur ormanı",
                    0
                ),
                Ecosystem(
                    2,
                    "Büyük Mercan Resifi",
                    "Okyanus",
                    null,
                    "Dünyanın en büyük mercan resifi",
                    0
                ),
                Ecosystem(3, "Sahra Çölü", "Çöl", null, "Dünyanın en büyük sıcak çölü", 0),
                Ecosystem(4, "Antarktika", "Kutup", null, "Dünyanın en soğuk kıtası", 0)
            )
            
            _ecosystems.value = ecosystemList
        } catch (e: Exception) {
            _error.value = "Ekosistem verileri yüklenirken hata oluştu: ${e.message}"
        }
    }
    
    /**
     * Tüm türleri yükler
     */
    private fun loadAllSpecies() {
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val speciesList = listOf(
                Species(
                    1,
                    "Jaguar",
                    "Panthera onca",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    "jaguar.png",
                    "Animal",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    2,
                    "Anakonda",
                    "Eunectes murinus",
                    "Orman",
                    "Dünyanın en büyük yılanlarından biri",
                    "anaconda.png",
                    "Animal",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    3,
                    "Orkinos",
                    "Thunnus thynnus",
                    "Okyanus",
                    "Hızlı yüzen büyük bir balık türü",
                    "tuna.png",
                    "Animal",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    4,
                    "Mercan",
                    "Anthozoa",
                    "Okyanus",
                    "Deniz ekosisteminin yapı taşları",
                    "coral.png",
                    "Plant",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    5,
                    "Deve",
                    "Camelus dromedarius",
                    "Çöl",
                    "Çöl koşullarına uyum sağlamış memeli",
                    "camel.png",
                    "Animal",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    6,
                    "Kaktüs",
                    "Cactaceae",
                    "Çöl",
                    "Su depolayabilen çöl bitkisi",
                    "cactus.png",
                    "Plant",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    7,
                    "Kutup Ayısı",
                    "Ursus maritimus",
                    "Kutup",
                    "Kuzey kutbunun en büyük avcısı",
                    "polar_bear.png",
                    "Animal",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    8,
                    "Penguen",
                    "Spheniscidae",
                    "Kutup",
                    "Antarktika'nın simge hayvanı",
                    "penguin.png",
                    "Animal",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                )
            )
            
            _allSpecies.value = speciesList
        } catch (e: Exception) {
            _error.value = "Tür verileri yüklenirken hata oluştu: ${e.message}"
        }
    }
    
    /**
     * Keşfedilen türleri filtreler
     */
    private fun filterDiscoveredSpecies() {
        val user = _currentUser.value ?: return
        val allSpeciesList = _allSpecies.value ?: return
        
        val discovered = allSpeciesList.filter { species ->
            user.collectedSpeciesIds.contains(species.id)
        }
        
        _discoveredSpecies.value = discovered
    }
    
    /**
     * Kullanıcı puanlarını günceller
     */
    fun updateUserPoints(points: Int) {
        val currentUserValue = _currentUser.value ?: return
        currentUserValue.addPoints(points)
        _currentUser.value = currentUserValue
    }
    
    /**
     * Yeni bir tür keşfedildiğinde kullanıcı koleksiyonunu günceller
     */
    fun discoverNewSpecies(speciesId: Int) {
        val currentUserValue = _currentUser.value ?: return
        val updatedCollectedSpeciesIds = currentUserValue.collectedSpeciesIds.toMutableList()
        
        if (!updatedCollectedSpeciesIds.contains(speciesId)) {
            updatedCollectedSpeciesIds.add(speciesId)
            
            val updatedUser = currentUserValue.copy(
                collectedSpeciesIds = updatedCollectedSpeciesIds,
                points = currentUserValue.points + 50 // Yeni tür keşfi için 50 puan
            )
            
            _currentUser.value = updatedUser
            
            // Keşfedilen türleri güncelle
            filterDiscoveredSpecies()
        }
    }
    
    /**
     * Kullanıcı ayarlarını günceller
     */
    fun updateUserSettings(parentalControlEnabled: Boolean) {
        val currentUserValue = _currentUser.value ?: return
        val updatedUser = currentUserValue.copy(
            parentalControlEnabled = parentalControlEnabled
        )
        
        _currentUser.value = updatedUser
    }
}
