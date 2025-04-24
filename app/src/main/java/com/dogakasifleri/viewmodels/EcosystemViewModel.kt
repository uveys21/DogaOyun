package com.dogakasifleri.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogakasifleri.models.Ecosystem
import com.dogakasifleri.models.Species
import com.dogakasifleri.repositories.EcosystemRepositoryImpl
import com.dogakasifleri.repositories.SpeciesRepositoryImpl

/**
 * EcosystemViewModel - Ekosistem verilerini yöneten ViewModel sınıfı
 * Bu sınıf, ekosistem ve türler hakkındaki verileri yönetir ve UI bileşenlerine sunar.
 */
class EcosystemViewModel : ViewModel() {

    private val _ecosystems = MutableLiveData<List<Ecosystem>>()
    val ecosystems: LiveData<List<Ecosystem>> = _ecosystems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val ecosystemRepository = EcosystemRepositoryImpl()
    private val speciesRepository = SpeciesRepositoryImpl()

    /**
     * Tüm ekosistemleri yükler
     */
    fun loadAllEcosystems() {
        _isLoading.value = true
        
        try {
            
            val ecosystemList = mutableListOf<Ecosystem>()
            
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
        val ecosystemData = MutableLiveData<Ecosystem>()
        ecosystemData.value = ecosystemRepository.getEcosystemById(ecosystemId)
        return ecosystemData
    }

    /**
     * Belirli bir ekosistemdeki türleri yükler
     */
    fun getSpeciesForEcosystem(ecosystemId: Int): LiveData<List<Species>> {
        val speciesInEcosystem = MutableLiveData<List<Species>>()
        speciesInEcosystem.value = speciesRepository.getSpeciesByEcosystemId(ecosystemId)
        return speciesInEcosystem
    }
}
