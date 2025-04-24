package com.dogakasifleri.utils

import android.content.Context
import androidx.core.content.edit

/**
 * Koleksiyon Yöneticisi - Kullanıcının keşfettiği türleri yönetir
 */
class CollectionManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("Collection", Context.MODE_PRIVATE)

    /**
     * Koleksiyona tür ekler
     */
    fun addToCollection(speciesId: Int) {
        sharedPreferences.edit() {
            putBoolean("species_$speciesId", true)
        }
    }

    /**
     * Koleksiyondan tür çıkarır
     */
    fun removeFromCollection(speciesId: Int) {
        val editor = sharedPreferences.edit()
        editor.remove("species_$speciesId")
        editor.apply()
    }

    /**
     * Türün koleksiyonda olup olmadığını kontrol eder
     */
    fun isInCollection(speciesId: Int): Boolean {
        return sharedPreferences.getBoolean("species_$speciesId", false)
    }

    /**
     * Koleksiyondaki tüm türlerin ID'lerini döndürür
     */
    fun getCollectedSpecies(): List<Int> {
        val collectedSpecies = mutableListOf<Int>()
        
        // Tüm kayıtları kontrol et
        val allEntries = sharedPreferences.all
        
        for (entry in allEntries) {
            // Tür ID'lerini ayıkla
            if (entry.key.startsWith("species_") && entry.value == true) {
                val speciesId = entry.key.removePrefix("species_").toIntOrNull()
                if (speciesId != null) {
                    collectedSpecies.add(speciesId)
                }
            }
        }
        
        return collectedSpecies
    }

    /**
     * Koleksiyondaki tür sayısını döndürür
     */
    fun getCollectionCount(): Int {
        return getCollectedSpecies().size
    }

    /**
     * Koleksiyonu temizler
     */
    fun clearCollection() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
