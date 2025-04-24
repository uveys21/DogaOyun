package com.dogakasifleri.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogakasifleri.models.Ecosystem
import com.dogakasifleri.models.Species

/**
 * EcosystemViewModel - Ekosistem verilerini yöneten ViewModel sınıfı
 * Bu sınıf, ekosistem ve türler hakkındaki verileri yönetir ve UI bileşenlerine sunar.
 */
class EcosystemViewModel : ViewModel() {

    private val _ecosystem = MutableLiveData<Ecosystem?>()
    val ecosystem: LiveData<Ecosystem> = _ecosystem

    private val _ecosystems = MutableLiveData<List<Ecosystem>>()
    val ecosystems: LiveData<List<Ecosystem>> = _ecosystems

    private val _speciesInEcosystem = MutableLiveData<List<Species>>()
    val speciesInEcosystem: LiveData<List<Species>> = _speciesInEcosystem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Tüm ekosistemleri yükler
     */
    fun loadAllEcosystems() {
        _isLoading.value = true
        
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
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Ekosistemler yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
    }
    
    /**
     * Belirli bir ekosistemi yükler
     */
    fun getEcosystem(ecosystemId: Int): LiveData<Ecosystem> {
        _isLoading.value = true
        
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val ecosystemList = listOf(
                Ecosystem(
                    1,
                    "Amazon Yağmur Ormanı",
                    "Orman",
                    null,
                    "Dünyanın en büyük yağmur ormanı ve biyolojik çeşitlilik açısından en zengin bölgelerinden biridir. Binlerce bitki ve hayvan türüne ev sahipliği yapar.",
                    0
                ),
                Ecosystem(
                    2,
                    "Büyük Mercan Resifi",
                    "Okyanus",
                    null,
                    "Avustralya'nın kuzeydoğu kıyısı boyunca uzanan dünyanın en büyük mercan resifi. Binlerce farklı deniz canlısına ev sahipliği yapar.",
                    0
                ),
                Ecosystem(
                    3,
                    "Sahra Çölü",
                    "Çöl",
                    null,
                    "Afrika'nın kuzeyinde yer alan dünyanın en büyük sıcak çölü. Zorlu koşullara rağmen birçok bitki ve hayvan türü burada yaşamayı başarır.",
                    0
                ),
                Ecosystem(
                    4,
                    "Antarktika",
                    "Kutup",
                    null,
                    "Dünyanın en soğuk kıtası ve en zorlu yaşam alanlarından biri. Penguen, fok ve balina gibi soğuğa dayanıklı türlere ev sahipliği yapar.",
                    0
                )
            )
            
            val ecosystem = ecosystemList.find { it.id == ecosystemId }
            if (ecosystem != null) {
                _ecosystem.value = ecosystem
            } else {
                _error.value = "Ekosistem bulunamadı"
            }
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Ekosistem yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return ecosystem
    }
    
    /**
     * Belirli bir ekosistemdeki türleri yükler
     */
    fun getSpeciesForEcosystem(ecosystemId: Int): LiveData<List<Species>> {
        _isLoading.value = true
        
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val allSpecies = listOf(
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
            
            // Ekosistem tipini bul
            val ecosystem = getEcosystem(ecosystemId).value
            val ecosystemType = ecosystem?.type ?: ""
            
            // Ekosistem tipine göre türleri filtrele
            val filteredSpecies = allSpecies.filter { it.ecosystemType == ecosystemType }
            
            _speciesInEcosystem.value = filteredSpecies
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Türler yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _speciesInEcosystem
    }
    
    /**
     * Ekosistem tipine göre türleri yükler
     */
    fun getSpeciesByEcosystemType(ecosystemType: String): LiveData<List<Species>> {
        _isLoading.value = true
        
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val allSpecies = listOf(
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
            
            // Ekosistem tipine göre türleri filtrele
            val filteredSpecies = allSpecies.filter { it.ecosystemType == ecosystemType }
            
            _speciesInEcosystem.value = filteredSpecies
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Türler yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
        
        return _speciesInEcosystem
    }
}
