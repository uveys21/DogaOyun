package com.dogakasifleri.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogakasifleri.models.CollectionItem
import com.dogakasifleri.utils.CollectionManager

/**
 * CollectionViewModel - Koleksiyon verilerini yöneten ViewModel sınıfı
 * Bu sınıf, kullanıcının topladığı bitki, hayvan ve diğer doğa öğelerinin
 * verilerini yönetir ve UI bileşenlerine sunar.
 */
class CollectionViewModel : ViewModel() {

    private val _collectionItems = MutableLiveData<List<CollectionItem>>()
    val collectionItems: LiveData<List<CollectionItem>> = _collectionItems

    private val _filteredItems = MutableLiveData<List<CollectionItem>>()
    val filteredItems: LiveData<List<CollectionItem>> = _filteredItems

    private val _favoriteItems = MutableLiveData<List<CollectionItem>>()
    val favoriteItems: LiveData<List<CollectionItem>> = _favoriteItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Tüm koleksiyon öğelerini yükler
     */
    fun loadAllCollectionItems(userId: String) {
        _isLoading.value = true
        
        try {
            // Normalde bu veriler bir veritabanından veya API'den gelir
            // Şimdilik örnek veriler kullanıyoruz
            val collectionItemsList = listOf(
                CollectionItem(
                    id = 1,
                    name = "Jaguar",
                    type = "Animal",
                    imageUrl = "jaguar.png",
                    description = "Yağmur ormanlarının en güçlü avcısı",
                    ecosystemType = "Orman",
                    discoveryDate = System.currentTimeMillis() - 86400000, // 1 gün önce
                    rarity = "Rare",
                    referenceId = 1
                ),
                CollectionItem(
                    id = 2,
                    name = "Anakonda",
                    type = "Animal",
                    imageUrl = "anaconda.png",
                    description = "Dünyanın en büyük yılanlarından biri",
                    ecosystemType = "Orman",
                    discoveryDate = System.currentTimeMillis() - 172800000, // 2 gün önce
                    rarity = "Uncommon",
                    referenceId = 2
                ),
                CollectionItem(
                    id = 3,
                    name = "Orkinos",
                    type = "Animal",
                    imageUrl = "tuna.png",
                    description = "Hızlı yüzen büyük bir balık türü",
                    ecosystemType = "Okyanus",
                    discoveryDate = System.currentTimeMillis() - 259200000, // 3 gün önce
                    rarity = "Common",
                    referenceId = 3
                ),
                CollectionItem(
                    id = 4,
                    name = "Penguen",
                    type = "Animal",
                    imageUrl = "penguin.png",
                    description = "Antarktika'nın simge hayvanı",
                    ecosystemType = "Kutup",
                    discoveryDate = System.currentTimeMillis() - 345600000, // 4 gün önce
                    rarity = "Uncommon",
                    referenceId = 8,
                    isFavorite = true
                ),
                CollectionItem(
                    id = 5,
                    name = "Deve",
                    type = "Animal",
                    imageUrl = "camel.png",
                    description = "Çöl koşullarına uyum sağlamış memeli",
                    ecosystemType = "Çöl",
                    discoveryDate = System.currentTimeMillis() - 432000000, // 5 gün önce
                    rarity = "Common",
                    referenceId = 5
                )
            )
            
            _collectionItems.value = collectionItemsList
            _filteredItems.value = collectionItemsList
            
            // Favori öğeleri filtrele
            filterFavoriteItems()
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Koleksiyon öğeleri yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
    }
    
    /**
     * Koleksiyon öğelerini türe göre filtreler
     */
    fun filterItemsByType(type: String) {
        val allItems = _collectionItems.value ?: return
        
        if (type.isEmpty() || type == "All") {
            _filteredItems.value = allItems
        } else {
            _filteredItems.value = allItems.filter { it.type == type }
        }
    }
    
    /**
     * Koleksiyon öğelerini ekosisteme göre filtreler
     */
    fun filterItemsByEcosystem(ecosystemType: String) {
        val allItems = _collectionItems.value ?: return
        
        if (ecosystemType.isEmpty() || ecosystemType == "All") {
            _filteredItems.value = allItems
        } else {
            _filteredItems.value = allItems.filter { it.ecosystemType == ecosystemType }
        }
    }
    
    /**
     * Koleksiyon öğelerini nadirlik seviyesine göre filtreler
     */
    fun filterItemsByRarity(rarity: String) {
        val allItems = _collectionItems.value ?: return
        
        if (rarity.isEmpty() || rarity == "All") {
            _filteredItems.value = allItems
        } else {
            _filteredItems.value = allItems.filter { it.rarity == rarity }
        }
    }
    
    /**
     * Koleksiyon öğelerini arama sorgusuna göre filtreler
     */
    fun searchItems(query: String) {
        val allItems = _collectionItems.value ?: return
        
        if (query.isEmpty()) {
            _filteredItems.value = allItems
        } else {
            _filteredItems.value = allItems.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.description.contains(query, ignoreCase = true) 
            }
        }
    }
    
    /**
     * Favori koleksiyon öğelerini filtreler
     */
    fun filterFavoriteItems() {
        val allItems = _collectionItems.value ?: return
        _favoriteItems.value = allItems.filter { it.isFavorite }
    }
    
    /**
     * Koleksiyon öğesini favori olarak işaretler veya favoriden kaldırır
     */
    fun toggleFavorite(itemId: Int) {
        val allItems = _collectionItems.value?.toMutableList() ?: return
        val index = allItems.indexOfFirst { it.id == itemId }
        
        if (index != -1) {
            val item = allItems[index]
            val updatedItem = item.copy(isFavorite = !item.isFavorite)
            allItems[index] = updatedItem
            
            _collectionItems.value = allItems
            _filteredItems.value = _filteredItems.value?.map {
                if (it.id == itemId) updatedItem else it
            }
            
            // Favori öğeleri güncelle
            filterFavoriteItems()
        }
    }
    
    /**
     * Koleksiyon öğesine not ekler
     */
    fun addNoteToItem(itemId: Int, note: String) {
        val allItems = _collectionItems.value?.toMutableList() ?: return
        val index = allItems.indexOfFirst { it.id == itemId }
        
        if (index != -1) {
            val item = allItems[index]
            val updatedItem = item.copy(notes = note)
            allItems[index] = updatedItem
            
            _collectionItems.value = allItems
            _filteredItems.value = _filteredItems.value?.map {
                if (it.id == itemId) updatedItem else it
            }
            
            // Favori öğeleri güncelle
            if (item.isFavorite) {
                filterFavoriteItems()
            }
        }
    }
    
    /**
     * Koleksiyon öğesine etiket ekler
     */
    fun addTagToItem(itemId: Int, tag: String) {
        val allItems = _collectionItems.value?.toMutableList() ?: return
        val index = allItems.indexOfFirst { it.id == itemId }
        
        if (index != -1) {
            val item = allItems[index]
            val updatedTags = item.tags.toMutableList()
            
            if (!updatedTags.contains(tag)) {
                updatedTags.add(tag)
                
                val updatedItem = item.copy(tags = updatedTags)
                allItems[index] = updatedItem
                
                _collectionItems.value = allItems
                _filteredItems.value = _filteredItems.value?.map {
                    if (it.id == itemId) updatedItem else it
                }
                
                // Favori öğeleri güncelle
                if (item.isFavorite) {
                    filterFavoriteItems()
                }
            }
        }
    }
}
